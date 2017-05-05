package hung.views;

import hung.viewmodels.GamePlayViewModel;
import javafx.scene.layout.Pane;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class GamePlayPane extends Pane implements RootPane.Helper {

    private RootPane rootPane;

    private GamePlayViewModel viewModel;

    public GamePlayPane(RootPane rootPane, GamePlayViewModel viewModel) {
        this.rootPane = rootPane;
        this.viewModel = viewModel;

        setStyle("-fx-background-color: rgba(0,0,255);");
    }


    @Override
    public RootPane getRootPane() {
        return rootPane;
    }
}
