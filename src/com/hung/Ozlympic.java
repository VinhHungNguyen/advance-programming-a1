package com.hung;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Ozlympic {

    private List<Swimmer> swimmers;
    private List<Cyclist> cyclists;
    private List<Sprinter> sprinters;
    private List<SuperAthlete> superAthletes;

    private List<Official> officials;

    private List<Game> swimmingGames;
    private List<Game> cyclingGames;
    private List<Game> runningGames;

    public static void main(String[] args) {
        Ozlympic ozlympic = new Ozlympic();
        ozlympic.start();
    }

    public Ozlympic() {
        swimmers = new ArrayList<>();
        cyclists = new ArrayList<>();
        sprinters = new ArrayList<>();
        superAthletes = new ArrayList<>();

        officials = new ArrayList<>();

        swimmingGames = new ArrayList<>();
        cyclingGames = new ArrayList<>();
        runningGames = new ArrayList<>();

        makeHardCodedData();
    }

    /**
     * Start the Ozlympic game
     */
    private void start() {
        Scanner scanner = new Scanner(System.in);
        int selection = 0;

        do {
            printMenu();

            // Handle when the input selection is not an integer.
            if (!scanner.hasNextInt()) {
                alertInvalidSelection();
                scanner.next();
                continue;
            }

            selection = scanner.nextInt();

            // If the selection is not within 1 to 6
            if (selection < 1 || selection > 6) {
                alertInvalidSelection();
                continue;
            }

            if (selection == 1) {
                selectGameToRun();
            } else if (selection == 2) {
                predictWinner();
            } else if (selection == 3) {
                startGame();
            } else if (selection == 4) {
                displayResults();
            } else if (selection == 5) {
                displayAthletePoints();
            } else if (selection == 6) {
                break;
            }

            System.out.println();
        } while (true);
    }

    /**
     * Select a game to run
     */
    private void selectGameToRun() {

    }

    /**
     * Allow the user to predict the winner of that game
     */
    private void predictWinner() {

    }

    /**
     * Start the game
     */
    private void startGame() {

    }

    /**
     * Display the final results of all swimmingGames
     */
    private void displayResults() {

    }

    /**
     * Display the points of all athletes
     */
    private void displayAthletePoints() {

    }

    /**
     * Print the main menu
     */
    private void printMenu() {
        System.out.print("Ozlympic Game\n" +
                "===================================\n" +
                "1. Select a game to run\n" +
                "2. Predict the winner of the game\n" +
                "3. Start the game\n" +
                "4. Display the final results of all swimmingGames\n" +
                "5. Display the points of all athletes\n" +
                "6. Exit\n" +
                "Enter an option: ");
    }

    /**
     * Print alert message when the selection is invalid
     */
    private void alertInvalidSelection() {
        System.out.println("The selection must be an integer within 1 to 6.");
    }

    /**
     * Make hard coded data for the program
     */
    private void makeHardCodedData() {
        makeHardCodedDataForAthletes();
        makeHardCodedDataForGames();
    }

    /**
     * Make hard coded data for athletes
     */
    private void makeHardCodedDataForAthletes() {
        // Hard coded text for athletes
        String[][] swimmerStrings = {
                {"Donald", "VIC"}, {"Trump", "VIC"}, {"Barrack", "TAS"}, {"Obama", "TAS"},
                {"Bill", "NSW"}, {"Gates", "NSW"}, {"Steve", "QLD"}, {"Jobs", "QLD"}
        };
        String[][] cyclistStrings = {
                {"Beauty Name", "NSW"}, {"Strange Name", "QLD"}, {"His Name", "QLD"}, {"Who", "NSW"},
                {"Gengar", "Pokemon"}, {"Dragonite", "Pokemon"}, {"Rayquaza", "Pokemon"}
        };
        String[][] sprinterStrings = {
                {"Kyokushin", "Karate"}, {"Goju", "Karate"}, {"Shotokan", "Karate"}, {"Suzucho", "Karate"},
                {"Shitoryu", "Karate"}
        };
        String[][] superAthleteStrings = {
                {"Shinsengumi", "Kyoto"}, {"Hitokiri", "Tokyo"}, {"Hajime", "Okinawa"}, {"Aoshi", "Osaka"},
                {"Nobunaga", "Fuji"}, {"Iga", "Shinobi"}, {"Kouga", "Shinobi"}
        };

        int id = 0;

        // Setup swimmers
        for (int i = 0; i < swimmerStrings.length; i++) {
            String[] swimmerData = swimmerStrings[i];
            int age = 20 + i;
            swimmers.add(new Swimmer(id, swimmerData[0], swimmerData[1], age));
            id++;
        }

        // Setup cyclists
        for (int i = 0; i < cyclistStrings.length; i++) {
            String[] cyclistData = cyclistStrings[i];
            int age = 20 + i;
            cyclists.add(new Cyclist(id, cyclistData[0], cyclistData[1], age));
            id++;
        }

        // Setup sprinters
        for (int i = 0; i < sprinterStrings.length; i++) {
            String[] sprinterData = sprinterStrings[i];
            int age = 20 + i;
            sprinters.add(new Sprinter(id, sprinterData[0], sprinterData[1], age));
            id++;
        }

        // Setup super athletes
        for (int i = 0; i < superAthleteStrings.length; i++) {
            String[] superAthleteData = superAthleteStrings[i];
            int age = 20 + i;
            superAthletes.add(new SuperAthlete(id, superAthleteData[0], superAthleteData[1], age));
            id++;
        }
    }

    /**
     * Make hard coded data for games
     */
    private void makeHardCodedDataForGames() {
        int id = 0;
        List[] allGames = {swimmingGames, cyclingGames, runningGames};
        String[] idPrefixes = {Game.ID_PREFIX_SWIMMING, Game.ID_PREFIX_CYCLING, Game.ID_PREFIX_RUNNING};

        // Hard coded super athletes to add to each games.
        Athlete[][] athletes = {
                {superAthletes.get(0), superAthletes.get(1), superAthletes.get(2)},
                {superAthletes.get(3), superAthletes.get(4), superAthletes.get(5)},
                {superAthletes.get(6), superAthletes.get(0), superAthletes.get(3)},
                {superAthletes.get(1), superAthletes.get(2), superAthletes.get(4)}
        };

        int numberOfEachType = athletes.length; // The number of each type of games

        // Setup games
        for (int i = 0; i < allGames.length; i++) {
            List<Game> games = allGames[i];
            String idPrefix = idPrefixes[i];
            List moreAthletes = null; // Specific type athletes to add beside the super athletes above

            if (idPrefix.equals(Game.ID_PREFIX_SWIMMING)) {
                moreAthletes = swimmers;
            } else if (idPrefix.equals(Game.ID_PREFIX_CYCLING)) {
                moreAthletes = cyclists;
            } else if (idPrefix.equals(Game.ID_PREFIX_RUNNING)) {
                moreAthletes = sprinters;
            }

            // Setup each game for a type of game
            for (int j = 0; j < numberOfEachType; j++) {
                String gameId = idPrefix + String.format("%02d", id);
                Game g = new Game(gameId);

                // Add all super athletes
                g.addAllAthlete(athletes[j]);

                // Add specific type athletes
                for (int n = 0; n < j; n++) {
                    g.addAthlete((Athlete) moreAthletes.get(n));
                }

//                System.out.println(g.toString());
                games.add(g);
                id++;
            }
        }
    }
}
