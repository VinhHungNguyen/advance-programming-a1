package hung.workers;

import hung.models.Athlete;
import hung.models.Game;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;

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
//            List<Game> games = new ArrayList<>();
//            games.addAll(cyclingGames);
//            games.addAll(runningGames);
//            games.addAll(swimmingGames);

            for (Game g : games) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
                StringBuilder sb = new StringBuilder(g.getId());
                sb.append(", ").append(g.getOfficial().getId()).append(", ").append(sdf.format(g.getFinishingDate().getTime()));

                for (Athlete a : g.getAthletes()) {
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

    public static ObservableList<Game> getGames() {
        return games;
    }
}
