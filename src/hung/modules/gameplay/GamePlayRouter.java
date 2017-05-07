package hung.modules.gameplay;

import hung.modules.listview.ListViewPane;
import hung.modules.listview.ListViewViewModel;
import hung.views.RootPane;

/**
 * Created by hungnguyen on 5/5/17.
 */
public class GamePlayRouter {

    public void toGameResult(GamePlayPane gamePlayPane) {
        RootPane rootPane = gamePlayPane.getRootPane();
        ListViewViewModel viewModel = new ListViewViewModel();
        ListViewPane listViewPane = new ListViewPane(rootPane, viewModel, "Result");

        rootPane.addWithAnimation(listViewPane, () -> {
            rootPane.getChildren().remove(gamePlayPane);
        });
    }

    public void back(GamePlayPane gamePlayPane) {
        gamePlayPane.getRootPane().removeWithAnimation(gamePlayPane, null);
    }
}
