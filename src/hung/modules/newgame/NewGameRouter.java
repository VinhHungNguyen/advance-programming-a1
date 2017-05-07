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
     * @param officer
     * @param athletes
     * @param predictedAthlete
     */
    public void toRunGame(NewGamePane newGamePane, Official officer, ObservableList<Athlete> athletes, Athlete predictedAthlete) {
        // TODO: Handle validation for officer, athletes, and predicted athlete here


        RootPane rootPane = newGamePane.getRootPane();
        GamePlayViewModel gamePlayViewModel = new GamePlayViewModel(officer, athletes, predictedAthlete);
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
