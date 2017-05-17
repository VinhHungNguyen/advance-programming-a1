package hung.workers;

import hung.models.*;
import hung.utils.DatabaseUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by hungnguyen on 5/10/17.
 */
public class GameWorker {

    private static final String FIELD_ID = "id";
    private static final String FIELD_OFFICIAL_ID = "officialId";
    private static final String FIELD_DATE = "date";


    private static final String FIELD_GAME_ID = "gameId";
    private static final String FIELD_ATHLETE_ID = "athleteId";
    private static final String FIELD_ACHIEVE_TIME = "achieveTime";
    private static final String FIELD_REWARD_POINT = "rewardPoint";

    private static final String INSERT_GAME = "insert into " + DatabaseUtils.TABLE_GAMES
            + "(" + FIELD_ID
            + ", " + FIELD_OFFICIAL_ID
            + ", " + FIELD_DATE
            + ") values(?, ?, ?)";

    private static final String INSERT_GAME_PARTICIPANT = "insert into " + DatabaseUtils.TABLE_GAME_PARTICIPANT
            + "(" + FIELD_GAME_ID
            + ", " + FIELD_ATHLETE_ID
            + ", " + FIELD_ACHIEVE_TIME
            + ", " + FIELD_REWARD_POINT
            + ") values(?, ?, ?, ?)";


    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

    private static ObservableList<Game> games;

    /**
     * Load all games. By default, the games are loaded from database. If the database is not
     * available, the games will be loaded from "gameResults.txt".
     */
    public static void loadGames() {
        games = FXCollections.observableArrayList();

        Scanner scanner = null;
        Connection connection = null;

        try {
            connection = DatabaseUtils.getConnection();
            connection.setAutoCommit(false);

            // Return if successfully loading from database
            if (loadGamesFromDatabase(connection)) {
                System.out.println("Successfully load Games from database");
                connection.close();
//                server.stop();
                return;
            }

            // Start loading Games from file and insert them to the database

            System.out.println("Failed to load Games from database. " +
                    "Start loading from file and insert to database");

            PreparedStatement gameStatement = connection.prepareStatement(INSERT_GAME);
            PreparedStatement gameAthleteStatement = connection.prepareStatement(INSERT_GAME_PARTICIPANT);
            PreparedStatement participantStatement = connection.prepareStatement(ParticipantWorker.UPDATE_PARTICIPANT);
            File file = new File("gameResults.txt");

            if (!file.exists()) {
                return;
            }

            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] tokens = line.split(",");

                if (tokens.length < 3) {
                    continue;
                }

                String gameId = tokens[0].trim();
                String officialId = tokens[1].trim();
                String dateString = tokens[2].trim();
                Date finishingDate = simpleDateFormat.parse(dateString);
                List<String> athleteIds = new ArrayList<>();

                // Add game to batch
                gameStatement.setString(1, gameId);
                gameStatement.setString(2, officialId);
                gameStatement.setString(3, dateString);
                gameStatement.addBatch();

                // Loop throught all athletes of this game
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine().trim();

                    // Reach the end of this game
                    if (line.isEmpty()) {
                        break;
                    }

                    tokens = line.split(",");

                    if (tokens.length < 3) {
                        continue;
                    }

                    String athleteId = tokens[0].trim();
                    String achieveTime = tokens[1].trim();
                    String rewardPoint = tokens[2].trim();

                    // Add Game_Athlete to batch
                    gameAthleteStatement.setString(1, gameId);
                    gameAthleteStatement.setString(2, athleteId);
                    gameAthleteStatement.setString(3, achieveTime);
                    gameAthleteStatement.setString(4, rewardPoint);
                    gameAthleteStatement.addBatch();


                    Athlete a = ParticipantWorker.getAthleteById(athleteId);
                    a.setPreviousAchieveTime(Integer.parseInt(achieveTime));
                    a.setPreviousReceivedPoint(Integer.parseInt(rewardPoint));

                    participantStatement.setInt(1, a.getTotalPoint());
                    participantStatement.setString(2, a.getId());
                    participantStatement.addBatch();

                    athleteIds.add(athleteId);
                }

                // execute the batch
                gameStatement.executeBatch();
                gameAthleteStatement.executeBatch();
                participantStatement.executeBatch();

                connection.commit();

                gameStatement.close();
                gameAthleteStatement.close();

                Game game = new Game(gameId, officialId, athleteIds, finishingDate);
                games.add(game);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Load games from database
     * @return True if loading successful
     */
    private static boolean loadGamesFromDatabase(Connection connection) {
        System.out.println("Start loading Games from database");

        boolean tableNotExist = false;

        try {
            // If Game table does not exist yet, create it
            if (!DatabaseUtils.tableExisted(connection, DatabaseUtils.TABLE_GAMES)) {
                System.out.println("Game table not exist yet. Now create it.");

                tableNotExist = true;
                Statement statement = connection.createStatement();
                DatabaseUtils.dropTable(statement, DatabaseUtils.TABLE_GAMES);

                statement.executeUpdate("create table " + DatabaseUtils.TABLE_GAMES +
                        " (" + FIELD_ID + " VARCHAR(10), " +
                        FIELD_OFFICIAL_ID + " VARCHAR(9), " +
                        FIELD_DATE + " VARCHAR(30));");

                connection.commit();
                statement.close();
            }

            // If Game_Participant table does not exist yet, create it
            if (!DatabaseUtils.tableExisted(connection, DatabaseUtils.TABLE_GAME_PARTICIPANT)) {
                System.out.println("Game_Participant table not exist yet. Now create it.");

                tableNotExist = true;
                Statement statement = connection.createStatement();
                DatabaseUtils.dropTable(statement, DatabaseUtils.TABLE_GAME_PARTICIPANT);

                statement.executeUpdate("create table " + DatabaseUtils.TABLE_GAME_PARTICIPANT +
                        " (" + FIELD_GAME_ID + " VARCHAR(10), " +
                        FIELD_ATHLETE_ID + " VARCHAR(10), " +
                        FIELD_ACHIEVE_TIME + " VARCHAR(6), " +
                        FIELD_REWARD_POINT + " VARCHAR(6));");

                connection.commit();
                statement.close();
            }

            if (tableNotExist) {
                return false;
            }

            // Retrieve Games and Game_Participants
            Statement statement = connection.createStatement();
            ResultSet gameResultSet = DatabaseUtils.getAllRecords(statement, DatabaseUtils.TABLE_GAMES);
            ResultSet gameAthleteResultSet = DatabaseUtils.getAllRecords(statement, DatabaseUtils.TABLE_GAME_PARTICIPANT);
            HashMap<String, Game> games = new HashMap<>();

            while (gameResultSet.next()) {
                String gameId = gameResultSet.getString(FIELD_ID);
                String officialId = gameResultSet.getString(FIELD_OFFICIAL_ID);
                String dateString = gameResultSet.getString(FIELD_DATE);
                Date finishingDate = simpleDateFormat.parse(dateString);

                games.put(gameId, new Game(gameId, officialId, new ArrayList<>(), finishingDate));
            }

            while (gameAthleteResultSet.next()) {
                String gameId = gameAthleteResultSet.getString(FIELD_GAME_ID);
                String athleteId = gameAthleteResultSet.getString(FIELD_ATHLETE_ID);
                String achieveTime = gameAthleteResultSet.getString(FIELD_ACHIEVE_TIME);
                String rewardPoint = gameAthleteResultSet.getString(FIELD_REWARD_POINT);

                System.out.println("GA: " + gameId + " - " + athleteId);

                Game g = games.get(gameId);
                g.addAthleteId(athleteId);
            }

            GameWorker.games.addAll(games.values());

            gameResultSet.close();
            gameAthleteResultSet.close();
            statement.close();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Write all game results to file from the beginning.
     */
    private static void writeAllGameResultsToFile() {
        File file = new File("gameResults.txt");
        Writer writer = null;

        try {
            writer = new PrintWriter(file);

            for (Game g : games) {
                StringBuilder sb = new StringBuilder(g.getId());
                sb.append(", ").append(g.getOfficialId()).append(", ").append(simpleDateFormat.format(g.getFinishingDate()));

                for (String id : g.getAthleteIds()) {
                    Athlete a = ParticipantWorker.getAthleteById(id);
                    sb.append("\n")
                            .append(a.getId()).append(", ")
                            .append(a.getPreviousAchieveTime()).append(", ")
                            .append(a.getPreviousReceivedPoint());
                }

                writer.write(sb.toString());
                writer.write("\n\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Insert the game to database and append to file
     * @param game
     * @return True if insert successfully
     */
    public static boolean insertGame(Game game) {
        games.add(game);
        insertGameToDatabase(game);
        writeGameToFile(game);
        return true;
    }

    /**
     * Insert game to database
     * @param game
     */
    private static void insertGameToDatabase(Game game) {
        Connection connection = null;

        try {
            connection = DatabaseUtils.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement gameStatement = connection.prepareStatement(INSERT_GAME);
            PreparedStatement gameAthleteStatement = connection.prepareStatement(INSERT_GAME_PARTICIPANT);
            PreparedStatement participantStatement = connection.prepareStatement(ParticipantWorker.UPDATE_PARTICIPANT);

            // Add game to batch
            gameStatement.setString(1, game.getId());
            gameStatement.setString(2, game.getOfficialId());
            gameStatement.setString(3, simpleDateFormat.format(game.getFinishingDate()));
            gameStatement.addBatch();

            // Add Game_Athlete to batch
            for (String athleteId : game.getAthleteIds()) {
                Athlete a = ParticipantWorker.getAthleteById(athleteId);
                String achieveTime = "" + a.getPreviousAchieveTime();
                String rewardPoint = "" + a.getPreviousReceivedPoint();

                gameAthleteStatement.setString(1, game.getId());
                gameAthleteStatement.setString(2, athleteId);
                gameAthleteStatement.setString(3, achieveTime);
                gameAthleteStatement.setString(4, rewardPoint);
                gameAthleteStatement.addBatch();

                // TODO:
                participantStatement.setInt(1, a.getTotalPoint());
                participantStatement.setString(2, a.getId());
                participantStatement.addBatch();
            }

            // execute the batch
            gameStatement.executeBatch();
            gameAthleteStatement.executeBatch();
            participantStatement.executeBatch();

            connection.commit();

            gameStatement.close();
            gameAthleteStatement.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Write game to file
     * @param game
     */
    private static void writeGameToFile(Game game) {
        File file = new File("gameResults.txt");
        Writer writer = null;

        try {
            writer = new PrintWriter(new FileOutputStream(file, true));

            StringBuilder sb = new StringBuilder(game.getId());
            sb.append(", ").append(game.getOfficialId()).append(", ").append(simpleDateFormat.format(game.getFinishingDate()));

            for (String id : game.getAthleteIds()) {
                Athlete a = ParticipantWorker.getAthleteById(id);
                sb.append("\n")
                        .append(a.getId()).append(", ")
                        .append(a.getPreviousAchieveTime()).append(", ")
                        .append(a.getPreviousReceivedPoint());
            }

//                writer.write(g.getGameResult());,
            writer.append(sb.toString());
            writer.append("\n\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Create a new game instance
     * @param idPrefix The prefix for game ID
     * @param officialId
     * @param athleteIds
     * @param predictedAthleteId
     * @return
     */
    public static Game makeNewGame(String idPrefix,
                                   String officialId, List<String> athleteIds, String predictedAthleteId) {
        String id = idPrefix + (games.size() + 1);
        Game game = new Game(id, officialId, athleteIds);
        game.setPredictedAthleteId(predictedAthleteId);
        return game;
    }

    public static String[][] getFinishedGamesAsStrings() {
        String[][] results = new String[games.size()][];

        for (int i = 0; i < results.length; i++) {
            Game g = games.get(i);
            Official official = ParticipantWorker.getOfficialById(g.getOfficialId());
            Athlete firstPlaceAthlete = ParticipantWorker.getAthleteById(g.getAthleteIds().get(0));
            Athlete secondPlaceAthlete = ParticipantWorker.getAthleteById(g.getAthleteIds().get(1));
            Athlete thirdPlaceAthlete = ParticipantWorker.getAthleteById(g.getAthleteIds().get(2));

            results[i] = new String[] {
                    g.getId(), official.getName(),
                    firstPlaceAthlete.getName(), secondPlaceAthlete.getName(), thirdPlaceAthlete.getName()
            };
        }

        return results;
    }

    public static ObservableList<Game> getGames() {
        return games;
    }
}
