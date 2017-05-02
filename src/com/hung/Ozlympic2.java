package com.hung;

import com.hung.models.*;
import com.hung.utils.ViewUtils;
import com.hung.views.MainMenuPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class Ozlympic2 extends Application {

    private MainMenuPane mainMenuPane;

    private List<Swimmer> swimmers;
    private List<Cyclist> cyclists;
    private List<Sprinter> sprinters;
    private List<SuperAthlete> superAthletes;

    private List<Official> officials;

    private List<Game> swimmingGames;
    private List<Game> cyclingGames;
    private List<Game> runningGames;

    public Ozlympic2() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadParticipants();


        mainMenuPane = new MainMenuPane();

        Scene scene = new Scene(mainMenuPane, ViewUtils.WINDOW_WIDTH, ViewUtils.WINDOW_HEIGHT);

        // Setup stage
        primaryStage.setTitle("Ozlympic");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Load all participants. By default, the participants are loaded from database. If the database is not
     * available, the participants will be loaded from "participants.txt".
     */
    private void loadParticipants() {
        swimmers = new ArrayList<>();
        cyclists = new ArrayList<>();
        sprinters = new ArrayList<>();
        superAthletes = new ArrayList<>();

        officials = new ArrayList<>();

        swimmingGames = new ArrayList<>();
        cyclingGames = new ArrayList<>();
        runningGames = new ArrayList<>();

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
            List<Game> games = new ArrayList<>();
            games.addAll(cyclingGames);
            games.addAll(runningGames);
            games.addAll(swimmingGames);

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
}

