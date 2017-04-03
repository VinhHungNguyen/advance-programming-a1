package com.hung;

import com.hung.models.*;
import com.hung.utils.Utils;

import java.util.*;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Ozlympic {

    private static final String MAIN_MENU_TEXT = "Ozlympic Game\n" +
            "===================================\n" +
            "1. Select a game to run\n" +
            "2. Predict the winner of the game\n" +
            "3. Start the game\n" +
            "4. Display the final results of all games\n" +
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
        // No game is currently selected
        if (selectedGame == null) {
            System.out.println("No game is currently selected.\n" +
                    "Please select a game (option 1) and then run it (option 3).\n");
            return;
        }

        // Selected game has not run yet.
        if (!selectedGame.isDoneRunning()) {
            System.out.println("Selected game has not run yet.\nPlease run the game (option 3) before predicting.\n");
            return;
        }

        List<Athlete> athletes = selectedGame.getAthletes();
        StringBuilder sb = new StringBuilder("List of participants:\n");

        for (int i = 0; i < athletes.size(); i++) {
            Athlete a = athletes.get(i);
            sb.append(i + 1).append(". ").append(a.getName()).append(" - ID: ").append(a.getId()).append("\n");
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
                public boolean onOptionSelected() { // Handle when an athlete is selected
//                    selectedGame.setPredictedAthleteId(athlete.getId());
//                    selectedGame.getOfficial().summarise(athletes);
//                    selectedGame.reset();

                    selectedGame.handlePrediction(athlete);

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
        // No game is currently selected
        if (selectedGame == null) {
            System.out.println("No game is currently selected.\nPlease select a game");
            return;
        }
        selectedGame.run();
    }

    /**
     * Display the final results of all games
     */
    private void displayResults() {
        List<Game> allGames = new ArrayList<>(swimmingGames);
        allGames.addAll(cyclingGames);
        allGames.addAll(runningGames);

        boolean hasResult = false;
        for (Game g : allGames) {
            if (g.displayResult()) {
                hasResult = true;
            }
        }

        if (!hasResult) {
            System.out.println("There is no result because no game has run yet.");
        }

        System.out.println();
    }

    /**
     * Display the points of all athletes
     */
    private void displayAthletePoints() {
        List<Athlete> allAthletes = new ArrayList<>(swimmers);
        allAthletes.addAll(cyclists);
        allAthletes.addAll(sprinters);
        allAthletes.addAll(superAthletes);

        // Sorting the list of athletes according to their total points
        Collections.sort(allAthletes,  new Comparator<Athlete>() {
            @Override
            public int compare(Athlete o1, Athlete o2) {
                if (o1.getTotalPoint() < o2.getTotalPoint()) {
                    return 1;
                } else if (o1.getTotalPoint() > o2.getTotalPoint()) {
                    return -1;
                }
                return 0;
            }
        });

        System.out.println("RANK:");
        System.out.println("=======================");

        for (int i = 0; i < allAthletes.size(); i++) {
            Athlete a = allAthletes.get(i);
            System.out.print("#" + (i + 1) + " ");
            System.out.println(a.getName() + " - ID: " + a.getId() + " - Points: " + a.getTotalPoint());
        }

        System.out.println();
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
                    System.out.println("Now select 4 to go back to main menu, or select another game.");
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
                {"Red Alert", "EA"}, {"Diablo", "Blizzard"}, {"Assassin Creed", "Ubisoft"}, {"Sparrow", "Caribbean"}
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
                {"Nobunaga", "Fuji"}, {"Iga", "Shinobi"}, {"Kouga", "Shinobi"},
                {"Bumblebee", "Autobot"}, {"Megatron", "Decepticon"}, {"Bruticus", "Conbaticon"},
                {"Galvatron", "Decepticon"}, {"Grimlock", "Dinobot"}
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
                {superAthletes.get(6), superAthletes.get(7), superAthletes.get(8)},
                {superAthletes.get(9), superAthletes.get(10), superAthletes.get(11)}
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

                // Add all super athletes and official
                g.addAllAthlete(athletes[j]);
                g.setOfficial(officials.get(j % officials.size()));

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
