package hung.workers;

import hung.models.*;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
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
                String line = scanner.nextLine().trim();
                String[] tokens = line.split(",");

                if (tokens.length < 5) {
                    continue;
                }

                String id = tokens[0].trim();
                String type = tokens[1].trim();
                String name = tokens[2].trim();
                int age = Integer.parseInt(tokens[3].trim());
                String state = tokens[4].trim();

                if (type.equals(Participant.TYPE_OFFICER)) {
                    Official official = new Official(id, name, state, age);
                    officials.put(id, official);
                } else if (type.equals(Participant.TYPE_SPRINTER)) {
                    Sprinter sprinter = new Sprinter(id, name, state, age);
                    sprinters.put(id, sprinter);
                } else if (type.equals(Participant.TYPE_SUPER)) {
                    SuperAthlete superAthlete = new SuperAthlete(id, name, state, age);
                    superAthletes.put(id, superAthlete);
                } else if (type.equals(Participant.TYPE_SWIMMER)) {
                    Swimmer swimmer = new Swimmer(id, name, state, age);
                    swimmers.put(id, swimmer);
                } else if (type.equals(Participant.TYPE_CYCLIST)) {
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
                if (o1.getTotalPoint() > o1.getTotalPoint()) {
                    return 1;
                } else if (o1.getTotalPoint() < o2.getTotalPoint()) {
                    return -1;
                }
                return 0;
            }
        });

        String[][] results = new String[allAthletes.size()][];

        for (int i = 0; i < results.length; i++) {
            Athlete a = allAthletes.get(i);
            results[i] = new String[] {
                    a.getId(), a.getName(), a.getPlayableGameIdPrefix(), a.getTotalPoint() + "", a.getState()
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
