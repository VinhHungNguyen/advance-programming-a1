package hung.views;

import hung.routers.GamePlayRouter;
import hung.viewmodels.GamePlayViewModel;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.awt.Button;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class GamePlayPane extends Pane implements RootPane.Helper {

    private RootPane rootPane;

    private GamePlayViewModel viewModel;
    private GamePlayRouter router;

    public GamePlayPane(RootPane rootPane, GamePlayViewModel viewModel) {
        this.rootPane = rootPane;
        this.viewModel = viewModel;
        router = new GamePlayRouter();

        // The Back button to go back to main scene
        javafx.scene.control.Button backButton = new javafx.scene.control.Button("Back");
        backButton.setOnAction(event -> {
            router.back(this);
        });

        setStyle("-fx-background-color: rgba(0,255,0);");
        getChildren().add(backButton);
    }


    @Override
    public RootPane getRootPane() {
        return rootPane;
    }
}
