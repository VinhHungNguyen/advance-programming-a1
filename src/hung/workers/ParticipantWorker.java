package hung.workers;

import hung.models.*;
import hung.utils.DatabaseUtils;
import org.hsqldb.Server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.*;
import java.util.*;

/**
 * Created by hungnguyen on 5/10/17.
 */
public class ParticipantWorker {

    private static final String FIELD_ID = "id";
    private static final String FIELD_TYPE = "type";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_AGE = "age";
    private static final String FIELD_STATE = "state";
    private static final String FIELD_SCORE = "score";

    private static final String INSERT_PARTICIPANT = "insert into " + DatabaseUtils.TABLE_PARTICIPANTS
            + "(" + FIELD_ID
            + ", " + FIELD_TYPE
            + ", " + FIELD_NAME
            + ", " + FIELD_AGE
            + ", " + FIELD_STATE
            + ", " + FIELD_SCORE
            + ") values(?, ?, ?, ?, ?, ?)";

    public static final String UPDATE_PARTICIPANT = "UPDATE " + DatabaseUtils.TABLE_PARTICIPANTS
            + " SET " + FIELD_SCORE + " = ? WHERE " + FIELD_ID + " = ?";

    private static Map<String, Swimmer> swimmers;
    private static Map<String, Cyclist> cyclists;
    private static Map<String, Sprinter> sprinters;
    private static Map<String, SuperAthlete> superAthletes;

    private static Map<String, Official> officials;


    /**
     * Load all participants. By default, the participants are loaded from database. If the database is not
     * available, the participants will be loaded from "participants.txt".
     */
    public static void loadParticipants() {
        swimmers = new LinkedHashMap<>();
        cyclists = new LinkedHashMap<>();
        sprinters = new LinkedHashMap<>();
        superAthletes = new LinkedHashMap<>();

        officials = new LinkedHashMap<>();

        Scanner scanner = null;
        Connection connection = null;
        Server server = DatabaseUtils.getServer();

        server.start();

        try {
            connection = DatabaseUtils.getConnection();
            connection.setAutoCommit(false);

            // Return if successfully loading from database
            if (loadParticipantsFromDatabase(connection)) {
                System.out.println("Successfully load Participants from database");
                connection.close();
                server.stop();
                return;
            }

            // Start loading participants from file and insert them to the database

            System.out.println("Failed to load participants from database. " +
                    "Start loading from file and insert to database");

            PreparedStatement prepareStatement = connection.prepareStatement(INSERT_PARTICIPANT);
            File file = new File("participants.txt");

            if (!file.exists()) {
                return;
            }

            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] tokens = line.split(",");

                // Continue if the current line can't be parsed
                if (!parseParticipants(tokens)) {
                    continue;
                }

                // Add the participant parsed from file to batch
                addParticipantToBatch(prepareStatement, tokens);
            }

            // execute the batch
            int[] updateCounts = prepareStatement.executeBatch();
            for (int i = 0; i < updateCounts.length; i++) {
                System.out.println("Participant Record Status: " + updateCounts[i]);
            }
            connection.commit();

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

        server.stop();
    }

    /**
     * Load participants from database
     * @return True if loading successful
     */
    private static boolean loadParticipantsFromDatabase(Connection connection) {
        System.out.println("Start loading Participants from database");

        try {
            // If Participant table does not exist yet, create it then return
            if (!DatabaseUtils.tableExisted(connection, DatabaseUtils.TABLE_PARTICIPANTS)) {
                System.out.println("Participant table not exist yet. Now create it.");

                Statement statement = connection.createStatement();
                DatabaseUtils.dropTable(statement, DatabaseUtils.TABLE_PARTICIPANTS);

                statement.executeUpdate("create table " + DatabaseUtils.TABLE_PARTICIPANTS +
                        " (" + FIELD_ID + " VARCHAR(10), " +
                        FIELD_TYPE + " VARCHAR(9), " +
                        FIELD_NAME + " VARCHAR(30), " +
                        FIELD_AGE + " int, " +
                        FIELD_STATE + " VARCHAR(10), " +
                        FIELD_SCORE + " int);");

                connection.commit();
                statement.close();
                return false;
            }

            // Retrieve participants
            Statement statement = connection.createStatement();
            ResultSet resultSet = DatabaseUtils.getAllRecords(statement, DatabaseUtils.TABLE_PARTICIPANTS);

            while (resultSet.next()) {
                parseParticipants(
                        resultSet.getString(FIELD_ID),
                        resultSet.getString(FIELD_TYPE),
                        resultSet.getString(FIELD_NAME),
                        resultSet.getInt(FIELD_AGE) + "",
                        resultSet.getString(FIELD_STATE),
                        resultSet.getInt(FIELD_SCORE) + "");
            }

            resultSet.close();
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
     * Parse given strings to get and add a participant
     * @param fields
     * @return
     */
    private static boolean parseParticipants(String... fields) {
        if (fields.length < 5) {
            return false;
        }

        String id = fields[0].trim();
        String type = fields[1].trim();
        String name = fields[2].trim();
        int age = Integer.parseInt(fields[3].trim());
        String state = fields[4].trim();
        int score = fields.length == 6 ? Integer.parseInt(fields[5].trim()) : 0;

        if (type.equals(Participant.TYPE_OFFICER)) {
            Official official = new Official(id, name, state, age);
            officials.put(id, official);
        } else if (type.equals(Participant.TYPE_SPRINTER)) {
            Sprinter sprinter = new Sprinter(id, name, state, age);
            sprinter.setTotalPoint(score);
            sprinters.put(id, sprinter);
        } else if (type.equals(Participant.TYPE_SUPER)) {
            SuperAthlete superAthlete = new SuperAthlete(id, name, state, age);
            superAthlete.setTotalPoint(score);
            superAthletes.put(id, superAthlete);
        } else if (type.equals(Participant.TYPE_SWIMMER)) {
            Swimmer swimmer = new Swimmer(id, name, state, age);
            swimmer.setTotalPoint(score);
            swimmers.put(id, swimmer);
        } else if (type.equals(Participant.TYPE_CYCLIST)) {
            Cyclist cyclist = new Cyclist(id, name, state, age);
            cyclist.setTotalPoint(score);
            cyclists.put(id, cyclist);
        } else {
            System.out.println(
                    "INVALID record: " + id + " - " + type + " - " + name + " - " + age + " - " + state);
            return false;
        }

        System.out.println("Parsed Participant: " + id + " - " + type + " - " + name + " - " + age + " - " + state);

        return true;
    }

    private static boolean addParticipantToBatch(PreparedStatement prepareStatement, String[] fields) throws SQLException {
        String id = fields[0].trim();
        String type = fields[1].trim();
        String name = fields[2].trim();
        String age = fields[3].trim();
        String state = fields[4].trim();

        // Add row to batch
        prepareStatement.setString(1, id);
        prepareStatement.setString(2, type);
        prepareStatement.setString(3, name);
        prepareStatement.setInt(4, Integer.parseInt(age));
        prepareStatement.setString(5, state);
        prepareStatement.setInt(6, 0);
        prepareStatement.addBatch();

        System.out.println("Add To Batch: " + id + " - " + type + " - " + name + " - " + age + " - " + state);

        return true;
    }

    public static void saveAll() {
        if (saveAllToDatabase()) {
            return;
        }

        List<Participant> allParticipants = getAllParticipants();
        File file = new File("participants.txt");
        Writer writer = null;

        try {
            writer = new PrintWriter(file);

            for (Participant p : allParticipants) {
                String type = "";
                if (p instanceof Official) {
                    type = Participant.TYPE_OFFICER;
                } else if (p instanceof Swimmer) {
                    type = Participant.TYPE_SWIMMER;
                } else if (p instanceof Cyclist) {
                    type = Participant.TYPE_CYCLIST;
                } else if (p instanceof Sprinter) {
                    type = Participant.TYPE_SPRINTER;
                } else {
                    type = Participant.TYPE_SUPER;
                }

                    StringBuilder sb = new StringBuilder(p.getId());
                sb.append(", ").append(type).append(", ").append(p.getName());
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

    private static boolean saveAllToDatabase() {
        return false;
    }

    public static void updateAthlete(Athlete athlete, Connection connection, PreparedStatement statement) {

    }

    public static void updateAthlete(Athlete athlete) {
        Connection connection = null;
        Server server = DatabaseUtils.getServer();

        server.start();

        try {
            connection = DatabaseUtils.getConnection();
            connection.setAutoCommit(false);

            PreparedStatement statement = connection.prepareStatement(UPDATE_PARTICIPANT);
            statement.setInt(1, athlete.getTotalPoint());
            statement.setString(2, athlete.getId());
            statement.addBatch();
            statement.executeBatch();

            connection.commit();
            statement.close();

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

        server.stop();
    }

    public static Participant getParticipantById(String id) {
        if (officials.containsKey(id)) {
            return getOfficialById(id);
        }
        return getAthleteById(id);
    }

    public static Athlete getAthleteById(String id) {
        if (swimmers.containsKey(id)) {
            return getSwimmerById(id);
        }
        if (cyclists.containsKey(id)) {
            return getCyclistById(id);
        }
        if (sprinters.containsKey(id)) {
            return getSprinterById(id);
        }
        if (superAthletes.containsKey(id)) {
            return getSuperAthleteById(id);
        }
        return null;
    }

    public static String[][] getAllAthletesAsStrings() {
        List<Athlete> allAthletes = getAllAthletes();

        Collections.sort(allAthletes,  new Comparator<Athlete>() {
            @Override
            public int compare(Athlete o1, Athlete o2) {
                if (o1.getTotalPoint() < o1.getTotalPoint()) {
                    return 1;
                } else if (o1.getTotalPoint() > o2.getTotalPoint()) {
                    return -1;
                }
                return 0;
            }
        });

        String[][] results = new String[allAthletes.size()][];

        for (int i = 0; i < results.length; i++) {
            Athlete a = allAthletes.get(i);
            results[i] = new String[] {
                    a.getId(), a.getName(), a.getType(), a.getTotalPoint() + "", a.getState()
            };
        }

        return results;
    }

    private static List<Athlete> getAllAthletes() {
        List<Athlete> allAthletes = new ArrayList<>();
        allAthletes.addAll(swimmers.values());
        allAthletes.addAll(cyclists.values());
        allAthletes.addAll(sprinters.values());
        allAthletes.addAll(superAthletes.values());
        return allAthletes;
    }

    private static List<Participant> getAllParticipants() {
        List<Participant> allParticipants = new ArrayList<>();
        allParticipants.addAll(swimmers.values());
        allParticipants.addAll(cyclists.values());
        allParticipants.addAll(sprinters.values());
        allParticipants.addAll(superAthletes.values());
        allParticipants.addAll(officials.values());
        return allParticipants;
    }

    public static Swimmer getSwimmerById(String id) {
        return swimmers.get(id);
    }

    public static Cyclist getCyclistById(String id) {
        return cyclists.get(id);
    }

    public static Sprinter getSprinterById(String id) {
        return sprinters.get(id);
    }

    public static SuperAthlete getSuperAthleteById(String id) {
        return superAthletes.get(id);
    }

    public static Official getOfficialById(String id) {
        return officials.get(id);
    }

    public static Collection<Swimmer> getSwimmers() {
        return swimmers.values();
    }

    public static Collection<Cyclist> getCyclists() {
        return cyclists.values();
    }

    public static Collection<Sprinter> getSprinters() {
        return sprinters.values();
    }

    public static Collection<SuperAthlete> getSuperAthletes() {
        return superAthletes.values();
    }

    public static Collection<Official> getOfficials() {
        return officials.values();
    }

}
