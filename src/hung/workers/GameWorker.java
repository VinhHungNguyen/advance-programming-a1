package hung.workers;

import hung.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by hungnguyen on 5/10/17.
 */
public class GameWorker {

    private static ObservableList<Game> games;

    /**
     * Load all games. By default, the games are loaded from database. If the database is not
     * available, the games will be loaded from "gameResults.txt".
     */
    public static void loadGames() {
        games = FXCollections.observableArrayList();

        if (loadGamesFromDatabase()) {
            return;
        }

        File file = new File("gameResults.txt");
        if (!file.exists()) {
            return;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] tokens = line.split(",");

                if (tokens.length < 3) {
                    continue;
                }

                String gameId = tokens[0].trim();
                String officialId = tokens[1].trim();
                String dateString = tokens[2].trim();
                Date finishingDate = sdf.parse(dateString);
                List<String> athleteIds = new ArrayList<>();

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

                    athleteIds.add(athleteId);
                }

                Game game = new Game(gameId, officialId, athleteIds, finishingDate);
                games.add(game);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Load games from database
     * @return True if loading successful
     */
    private static boolean loadGamesFromDatabase() {
        return false;
    }

    /**
     * Write all game results to file from the beginning.
     */
    private static void writeAllGameResultsToFile() {
        File file = new File("gameResults.txt");
        Writer writer = null;

        try {
            writer = new PrintWriter(file);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
//            List<Game> games = new ArrayList<>();
//            games.addAll(cyclingGames);
//            games.addAll(runningGames);
//            games.addAll(swimmingGames);

            for (Game g : games) {
                StringBuilder sb = new StringBuilder(g.getId());
                sb.append(", ").append(g.getOfficialId()).append(", ").append(sdf.format(g.getFinishingDate()));

                for (String id : g.getAthleteIds()) {
                    Athlete a = ParticipantWorker.getAthleteById(id);
                    sb.append("\n")
                            .append(a.getId()).append(", ")
                            .append(a.getPreviousAchieveTime()).append(", ")
                            .append(a.getPreviousReceivedPoint());
                }

//                writer.write(g.getGameResult());
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

    public boolean insertGame(Game game) {
        games.add(game);
        return true;
    }

    public static Game makeNewGame(String idPrefix,
                                   String officialId, List<String> athleteIds, String predictedAthleteId) {
        String id = idPrefix + (games.size() + 1);
        Game game = new Game(id, officialId, athleteIds);
        game.setPredictedAthleteId(predictedAthleteId);
        return game;
    }

    public static ObservableList<Game> getGames() {
        return games;
    }
}
