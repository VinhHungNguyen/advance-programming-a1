package com.hung;

import com.hung.models.*;
import com.hung.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Ozlympic {

    private static final String MAIN_MENU_TEXT = "Ozlympic Game\n" +
            "===================================\n" +
            "1. Select a game to run\n" +
            "2. Predict the winner of the game\n" +
            "3. Start the game\n" +
            "4. Display the final results of all swimmingGames\n" +
            "5. Display the points of all athletes\n" +
            "6. Exit\n" +
            "Enter an option: ";
    private static final String GAME_MENU_TEXT = "Select a type of game:\n" +
            "1. Swimming\n" +
            "2. Cycling\n" +
            "3. Running\n" +
            "4. Back\n" +
            "Enter an option: ";

    private static final String INVALID_MAIN_MENU_SELECTION = "The selection must be an integer within 1 to 6.";
    private static final String INVALID_GAME_MENU_SELECTION = "The selection must be an integer within 1 to 4 " +
            "for swimming, cycling, running, or back respectively.";

    private List<Swimmer> swimmers;
    private List<Cyclist> cyclists;
    private List<Sprinter> sprinters;
    private List<SuperAthlete> superAthletes;

    private List<Official> officials;

    private List<Game> swimmingGames;
    private List<Game> cyclingGames;
    private List<Game> runningGames;

    private Game selectedGame;

    private int participantCount;

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
        Utils.runMenuFlow(MAIN_MENU_TEXT, INVALID_MAIN_MENU_SELECTION,
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() {
                        selectTypeOfGame();
                        return false;
                    }
                },
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() {
                        predictWinner();
                        return false;
                    }
                },
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() {
                        startGame();
                        return false;
                    }
                },
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() {
                        displayResults();
                        return false;
                    }
                },
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() {
                        displayAthletePoints();
                        return false;
                    }
                });
    }


    /**
     * Select a type of game to run
     */
    private void selectTypeOfGame() {
        Utils.runMenuFlow(GAME_MENU_TEXT, INVALID_GAME_MENU_SELECTION,
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() { // Select Swimming
                        selectGameToRun(swimmingGames);
                        return false;
                    }
                },
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() { // Select Cycling
                        selectGameToRun(cyclingGames);
                        return false;
                    }
                },
                new Utils.OnMenuOptionSelectedListener() {
                    @Override
                    public boolean onOptionSelected() { // Select Running
                        selectGameToRun(runningGames);
                        return false;
                    }
                });
    }

    /**
     * Allow the user to predict the winner of that game
     */
    private void predictWinner() {
        if (selectedGame == null) {
            System.out.println("No game is currently selected.\n Please select a game and then run it.\n");
            return;
        }

        List<Athlete> athletes = selectedGame.getAthletes();
        StringBuilder sb = new StringBuilder("\nList of participants:");

        for (int i = 0; i < athletes.size(); i++) {
            sb.append(i + 1).append(". ").append(athletes.get(i).toString());
        }

        sb.append("Enter your prediction: ");

        String menuText = sb.toString();
        sb = new StringBuilder("The selection must be an integer within 1 to ");
        sb.append(athletes.size() + 1).append(".\n");

        String alertMsg = sb.toString();
        Utils.OnMenuOptionSelectedListener[] listeners = new Utils.OnMenuOptionSelectedListener[athletes.size()];

        for (int i = 0; i < athletes.size(); i++) {
            final Athlete athlete = athletes.get(i);
            listeners[i] = new Utils.OnMenuOptionSelectedListener() {
                @Override
                public boolean onOptionSelected() {
                    selectedGame.setPredictedAthleteId(athlete.getId());

                    return true;
                }
            };
        }

        Utils.runMenuFlow(menuText, alertMsg, listeners);
    }

    /**
     * Start the game
     */
    private void startGame() {
        if (selectedGame == null) {
            System.out.println("No game is currently selected.\n Please select a game");
            return;
        }
        selectedGame.run();
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
     * Select a game of a selected type
     * @param games List of the games of the selected type
     */
    private void selectGameToRun(final List<Game> games) {
        StringBuilder sb = new StringBuilder("Select a game to run:\n");

        for (int i = 0; i < games.size(); i++) {
            sb.append(i + 1).append(". ").append(games.get(i).getId()).append("\n");
        }

        sb.append(games.size() + 1).append(". Back\n");
        sb.append("Enter an option: ");

        String menuText = sb.toString();

        sb = new StringBuilder("The selection must be an integer within 1 to ");
        sb.append(games.size() + 1).append(".\n");

        String alertMsg = sb.toString();
        Utils.OnMenuOptionSelectedListener[] listeners = new Utils.OnMenuOptionSelectedListener[games.size()];

        for (int i = 0; i < games.size(); i++) {
            final Game game = games.get(i);
            listeners[i] = new Utils.OnMenuOptionSelectedListener() {
                @Override
                public boolean onOptionSelected() {
                    selectedGame = game;
                    System.out.println("Selected game ID: " + selectedGame.getId());
                    return true;
                }
            };
        }

        Utils.runMenuFlow(menuText, alertMsg, listeners);
    }

    /**
     * Make hard coded data for the program
     */
    private void makeHardCodedData() {
        makeHardCodedDataForOfficials();
        makeHardCodedDataForAthletes();
        makeHardCodedDataForGames();
    }

    /**
     * Make hard coded data for officials
     */
    private void makeHardCodedDataForOfficials() {
        // Hard coded text for officials
        String[][] officialStrings = {
                {"Kyokushin", "Karate"}, {"Goju", "Karate"}, {"Shotokan", "Karate"}, {"Suzucho", "Karate"},
                {"Shitoryu", "Karate"}
        };

        // Setup officials
        for (int i = 0; i < officialStrings.length; i++) {
            String[] officialData = officialStrings[i];
            int age = 30 + i;
            officials.add(new Official(participantCount, officialData[0], officialData[1], age));
            participantCount++;
        }
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

        // Setup swimmers
        for (int i = 0; i < swimmerStrings.length; i++) {
            String[] swimmerData = swimmerStrings[i];
            int age = 20 + i;
            swimmers.add(new Swimmer(participantCount, swimmerData[0], swimmerData[1], age));
            participantCount++;
        }

        // Setup cyclists
        for (int i = 0; i < cyclistStrings.length; i++) {
            String[] cyclistData = cyclistStrings[i];
            int age = 20 + i;
            cyclists.add(new Cyclist(participantCount, cyclistData[0], cyclistData[1], age));
            participantCount++;
        }

        // Setup sprinters
        for (int i = 0; i < sprinterStrings.length; i++) {
            String[] sprinterData = sprinterStrings[i];
            int age = 20 + i;
            sprinters.add(new Sprinter(participantCount, sprinterData[0], sprinterData[1], age));
            participantCount++;
        }

        // Setup super athletes
        for (int i = 0; i < superAthleteStrings.length; i++) {
            String[] superAthleteData = superAthleteStrings[i];
            int age = 20 + i;
            superAthletes.add(new SuperAthlete(participantCount, superAthleteData[0], superAthleteData[1], age));
            participantCount++;
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
