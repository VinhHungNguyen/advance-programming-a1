package hung.modules.home;

import hung.modules.listview.ListViewPane;
import hung.modules.listview.ListViewViewModel;
import hung.modules.newgame.NewGamePane;
import hung.utils.ViewUtils;
import hung.views.RootPane;
import hung.workers.GameWorker;
import hung.workers.ParticipantWorker;
import javafx.animation.FadeTransition;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class HomeRouter {

    private FadeTransition fadeInTransition;

    public HomeRouter() {
        fadeInTransition = ViewUtils.makeFadeTransition(500, 0.0d, 1.0d);
    }

    /**
     * Go to new game pane
     * @param homePane
     */
    public void toNewGame(HomePane homePane) {
        NewGamePane newGamePane = new NewGamePane(homePane.getRootPane());
        ViewUtils.fadeIn(homePane.getRootPane(), newGamePane, fadeInTransition);
    }

    /**
     * Go to history pane
     * @param homePane
     */
    public void toHistory(HomePane homePane) {
        String[] headerContents = {"Game ID", "Officer Name", "First Place", "Second Place", "Third Place"};
        String[][] rowContents = GameWorker.getFinishedGamesAsStrings();

        RootPane rootPane = homePane.getRootPane();
        ListViewViewModel viewModel = new ListViewViewModel(headerContents, rowContents);
        ListViewPane listViewPane = new ListViewPane(rootPane, viewModel, "History");

        rootPane.addWithAnimation(listViewPane, null);
    }

    /**
     * Go to leader board pane
     * @param homePane
     */
    public void toLeaderboard(HomePane homePane) {
        String[] headerContents = {"Athlete ID", "Name", "Type", "Points", "State"};
        String[][] rowContents = ParticipantWorker.getAllAthletesAsStrings();

        RootPane rootPane = homePane.getRootPane();
        ListViewViewModel viewModel = new ListViewViewModel(headerContents, rowContents);
        ListViewPane listViewPane = new ListViewPane(rootPane, viewModel, "Leaderboard");

        rootPane.addWithAnimation(listViewPane, null);
    }
}
