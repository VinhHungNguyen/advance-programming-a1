package hung.viewmodels;

import hung.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

/**
 * Created by hungnguyen on 5/3/17.
 */
public class MainViewModel {

    private ObservableList<Swimmer> swimmers;
    private ObservableList<Cyclist> cyclists;
    private ObservableList<Sprinter> sprinters;
    private ObservableList<SuperAthlete> superAthletes;

    private ObservableList<Official> officials;

//    private List<Game> swimmingGames;
//    private List<Game> cyclingGames;
//    private List<Game> runningGames;
    private ObservableList<Game> games;

    public MainViewModel() {
        loadParticipants();

//        swimmingGames = new ArrayList<>();
//        cyclingGames = new ArrayList<>();
//        runningGames = new ArrayList<>();
        games = FXCollections.observableArrayList();
    }

    /**
     * Load all participants. By default, the participants are loaded from database. If the database is not
     * available, the participants will be loaded from "participants.txt".
     */
    private void loadParticipants() {
        swimmers = FXCollections.observableArrayList();
        cyclists = FXCollections.observableArrayList();
        sprinters = FXCollections.observableArrayList();
        superAthletes = FXCollections.observableArrayList();

        officials = FXCollections.observableArrayList();

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
                    officials.add(official);
                } else if (type.equals(Athlete.TYPE_SPRINTER)) {
                    Sprinter sprinter = new Sprinter(id, name, state, age);
                    sprinters.add(sprinter);
                } else if (type.equals(Athlete.TYPE_SUPER)) {
                    SuperAthlete superAthlete = new SuperAthlete(id, name, state, age);
                    superAthletes.add(superAthlete);
                } else if (type.equals(Athlete.TYPE_SWIMMER)) {
                    Swimmer swimmer = new Swimmer(id, name, state, age);
                    swimmers.add(swimmer);
                } else if (type.equals(Athlete.TYPE_CYCLIST)) {
                    Cyclist cyclist = new Cyclist(id, name, state, age);
                    cyclists.add(cyclist);
                } else {
                    System.out.println(
                            "INVALID record: " + id + " - " + type + " - " + name + " - " + age + " - " + state);
                    continue;
                }

                System.out.println("Record: " + id + " - " + type + " - " + name + " - " + age + " - " + state);
            }

            System.out.println("Official Size: " + officials.size());
            System.out.println("Sprinter Size: " + sprinters.size());
            System.out.println("Super Size: " + superAthletes.size());
            System.out.println("Swimmer Size: " + swimmers.size());
            System.out.println("Cyclist Size: " + cyclists.size());
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
    private boolean loadParticipantsFromDatabase() {
        return false;
    }

    /**
     * Write all game results to file from the beginning.
     */
    private void writeAllGameResultsToFile() {
        File file = new File("gameResults.txt");
        Writer writer = null;

        try {
            writer = new PrintWriter(file);
//            List<Game> games = new ArrayList<>();
//            games.addAll(cyclingGames);
//            games.addAll(runningGames);
//            games.addAll(swimmingGames);

            for (Game g : games) {
                writer.write(g.getGameResult());
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

    public ObservableList<Swimmer> getSwimmers() {
        return swimmers;
    }

    public ObservableList<Cyclist> getCyclists() {
        return cyclists;
    }

    public ObservableList<Sprinter> getSprinters() {
        return sprinters;
    }

    public ObservableList<SuperAthlete> getSuperAthletes() {
        return superAthletes;
    }

    public ObservableList<Official> getOfficials() {
        return officials;
    }

    public ObservableList<Game> getGames() {
        return games;
    }
}
