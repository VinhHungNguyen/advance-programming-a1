package hung.routers;

import hung.utils.ViewUtils;
import hung.viewmodels.MainViewModel;
import hung.viewmodels.NewGameViewModel;
import hung.views.MainPane;
import hung.views.NewGamePane;
import javafx.animation.FadeTransition;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class MainRouter {

    private FadeTransition fadeInTransition;

    public MainRouter() {
        fadeInTransition = ViewUtils.makeFadeTransition(500, 0.0d, 1.0d);
    }

    public void toNewGame(MainPane mainPane, MainViewModel viewModel) {
        NewGameViewModel newGameViewModel = new NewGameViewModel(viewModel.getSwimmers(), viewModel.getCyclists(),
                viewModel.getSprinters(), viewModel.getSuperAthletes(), viewModel.getOfficials());
        NewGamePane newGamePane = new NewGamePane(mainPane, newGameViewModel);

        ViewUtils.fadeIn(fadeInTransition, newGamePane, mainPane);
    }
}
