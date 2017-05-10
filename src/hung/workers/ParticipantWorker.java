package hung.workers;

import hung.models.*;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.*;

/**
 * Created by hungnguyen on 5/10/17.
 */
public class ParticipantWorker {

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

        if (loadParticipantsFromDatabase()) {
            return;
        }

        Scanner scanner = null;
        try {
            File file = new File("participants.txt");
            scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] tokens = line.split(",");

                if (tokens.length < 5) {
                    continue;
                }

                String id = tokens[0].trim();
                String type = tokens[1].trim();
                String name = tokens[2].trim();
                int age = Integer.parseInt(tokens[3].trim());
                String state = tokens[4].trim();

                if (type.equals(Athlete.TYPE_OFFICER)) {
                    Official official = new Official(id, name, state, age);
                    officials.put(id, official);
                } else if (type.equals(Athlete.TYPE_SPRINTER)) {
                    Sprinter sprinter = new Sprinter(id, name, state, age);
                    sprinters.put(id, sprinter);
                } else if (type.equals(Athlete.TYPE_SUPER)) {
                    SuperAthlete superAthlete = new SuperAthlete(id, name, state, age);
                    superAthletes.put(id, superAthlete);
                } else if (type.equals(Athlete.TYPE_SWIMMER)) {
                    Swimmer swimmer = new Swimmer(id, name, state, age);
                    swimmers.put(id, swimmer);
                } else if (type.equals(Athlete.TYPE_CYCLIST)) {
                    Cyclist cyclist = new Cyclist(id, name, state, age);
                    cyclists.put(id, cyclist);
                } else {
                    System.out.println(
                            "INVALID record: " + id + " - " + type + " - " + name + " - " + age + " - " + state);
                    continue;
                }

                System.out.println("Record: " + id + " - " + type + " - " + name + " - " + age + " - " + state);
            }

//            System.out.println("Official Size: " + officials.size());
//            System.out.println("Sprinter Size: " + sprinters.size());
//            System.out.println("Super Size: " + superAthletes.size());
//            System.out.println("Swimmer Size: " + swimmers.size());
//            System.out.println("Cyclist Size: " + cyclists.size());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Load participants from database
     * @return True if loading successful
     */
    private static boolean loadParticipantsFromDatabase() {
        // TODO: load participants from database
        return false;
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
