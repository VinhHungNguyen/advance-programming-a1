package hung.routers;

import hung.models.Athlete;
import hung.models.Official;
import hung.utils.ViewUtils;
import hung.viewmodels.GamePlayViewModel;
import hung.views.GamePlayPane;
import hung.views.NewGamePane;
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


        GamePlayViewModel gamePlayViewModel = new GamePlayViewModel(officer, athletes, predictedAthlete);
        GamePlayPane gamePlayPane = new GamePlayPane(newGamePane.getRootPane(), gamePlayViewModel);

        newGamePane.getRootPane().addWithAnimation(gamePlayPane);

        new Thread(() -> {
            try {
                Thread.sleep(500);
                newGamePane.getRootPane().getChildren().remove(newGamePane);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void back(NewGamePane newGamePane) {
        ViewUtils.fadeOut(newGamePane.getRootPane(), newGamePane, 500);
    }
}
