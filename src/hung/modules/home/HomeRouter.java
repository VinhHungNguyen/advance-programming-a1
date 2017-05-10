package hung.modules.home;

import hung.modules.listview.ListViewPane;
import hung.modules.listview.ListViewViewModel;
import hung.modules.newgame.NewGamePane;
import hung.modules.newgame.NewGameViewModel;
import hung.utils.ViewUtils;
import hung.views.RootPane;
import javafx.animation.FadeTransition;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class HomeRouter {

    private FadeTransition fadeInTransition;

    public HomeRouter() {
        fadeInTransition = ViewUtils.makeFadeTransition(500, 0.0d, 1.0d);
    }

    public void toNewGame(HomePane homePane, HomeViewModel viewModel) {
//        NewGameViewModel newGameViewModel = new NewGameViewModel(viewModel.getSwimmers(), viewModel.getCyclists(),
//                viewModel.getSprinters(), viewModel.getSuperAthletes(), viewModel.getOfficials());
//        NewGameViewModel newGameViewModel = new NewGameViewModel();

        NewGamePane newGamePane = new NewGamePane(homePane.getRootPane());

        ViewUtils.fadeIn(homePane.getRootPane(), newGamePane, fadeInTransition);
    }

    public void toHistory(HomePane homePane) {
        RootPane rootPane = homePane.getRootPane();
        ListViewViewModel viewModel = new ListViewViewModel();
        ListViewPane listViewPane = new ListViewPane(rootPane, viewModel, "History");

        rootPane.addWithAnimation(listViewPane, null);
    }

    public void toLeaderboard(HomePane homePane) {
        RootPane rootPane = homePane.getRootPane();
        ListViewViewModel viewModel = new ListViewViewModel();
        ListViewPane listViewPane = new ListViewPane(rootPane, viewModel, "Leaderboard");

        rootPane.addWithAnimation(listViewPane, null);
    }
}
