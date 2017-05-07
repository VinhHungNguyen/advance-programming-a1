package hung.routers;

import hung.viewmodels.ResultViewModel;
import hung.views.GamePlayPane;
import hung.views.ResultPane;
import hung.views.RootPane;

/**
 * Created by hungnguyen on 5/5/17.
 */
public class GamePlayRouter {

    public void toGameResult(GamePlayPane gamePlayPane) {
        RootPane rootPane = gamePlayPane.getRootPane();
        ResultViewModel viewModel = new ResultViewModel();
        ResultPane resultPane = new ResultPane(rootPane, viewModel);

        rootPane.addWithAnimation(resultPane, () -> {
            rootPane.getChildren().remove(gamePlayPane);
        });
    }

    public void back(GamePlayPane gamePlayPane) {
        gamePlayPane.getRootPane().removeWithAnimation(gamePlayPane, null);
    }
}
