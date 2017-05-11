package hung.modules.newgame;

import hung.models.Athlete;
import hung.models.Official;
import hung.modules.gameplay.GamePlayPane;
import hung.modules.gameplay.GamePlayViewModel;
import hung.utils.ViewUtils;
import hung.views.RootPane;
import javafx.collections.ObservableList;

/**
 * Created by hungnguyen on 5/5/17.
 */
public class NewGameRouter {

    /**
     * Move to run game when Ok button is clicked
     * @param newGamePane
     */
    public void toRunGame(NewGamePane newGamePane) {
        // TODO: Handle validation for officer, athletes, and predicted athlete here
//        if (officer == null) {
//
//        }
//        if (athletes == null || athletes.isEmpty()) {
//
//        }
//        if (predictedAthlete == null) {
//
//        }

        NewGameViewModel newGameViewModel = newGamePane.getViewModel();
        RootPane rootPane = newGamePane.getRootPane();
        GamePlayViewModel gamePlayViewModel = new GamePlayViewModel(
                newGameViewModel.getSelectedOfficer(),
                newGameViewModel.getSelectedAthletes(),
                newGameViewModel.getPredictedAthlete());
        GamePlayPane gamePlayPane = new GamePlayPane(rootPane, gamePlayViewModel);

        rootPane.addWithAnimation(gamePlayPane, () -> {
            rootPane.getChildren().remove(newGamePane);
            gamePlayPane.startCounting();
        });
    }

    public void back(NewGamePane newGamePane) {
        ViewUtils.fadeOut(newGamePane.getRootPane(), newGamePane, 500);
    }
}
