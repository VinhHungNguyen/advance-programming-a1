package hung.views;

import hung.routers.GamePlayRouter;
import hung.utils.ViewUtils;
import hung.viewmodels.GamePlayViewModel;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;


/**
 * Created by hungnguyen on 5/4/17.
 */
public class GamePlayPane extends Pane implements RootPane.Helper {

    private RootPane rootPane;

    private Label counterLabel;
    private StackPane counterPane;

    private ParallelTransition counterTrasition;

    private GamePlayViewModel viewModel;
    private GamePlayRouter router;

    public GamePlayPane(RootPane rootPane, GamePlayViewModel viewModel) {
        this.rootPane = rootPane;
        this.viewModel = viewModel;
        router = new GamePlayRouter();

        setupCounterView();
        setupAnimations();

        // The Back button to go back to main scene
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            router.back(this);
        });

        setStyle("-fx-background-color: rgba(0,255,0);");
        getChildren().add(backButton);
    }

    private void setupCounterView() {
        counterLabel = new Label();
        counterLabel.textProperty().bind(viewModel.counterStringProperty());
        counterLabel.setStyle("" +
                "-fx-font-size: 48px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: rgba(0,0,255);");

        counterPane = new StackPane();
        counterPane.getChildren().add(counterLabel);
        ViewUtils.equalizePane(counterPane, this);

        getChildren().add(counterPane);
    }

    private void setupAnimations() {
        FadeTransition fadeTransition = ViewUtils.makeFadeOutTransition(1000);
        ScaleTransition scaleTransition = new ScaleTransition();

        fadeTransition.setNode(counterLabel);
        fadeTransition.setCycleCount(1);

        scaleTransition.setNode(counterLabel);
        scaleTransition.setCycleCount(1);
        scaleTransition.setByX(-counterLabel.getPrefWidth() * 2);
        scaleTransition.setByY(-counterLabel.getPrefHeight() * 2);
        scaleTransition.setDuration(Duration.millis(1000));
        scaleTransition.setOnFinished(event -> {
            if (viewModel.getCounter() == 1) {
                getChildren().remove(counterPane);
                router.toGameResult(this);

                return;
            }

            viewModel.nextCount();
        });

        counterTrasition = new ParallelTransition(fadeTransition, scaleTransition);
        counterTrasition.setCycleCount(3);
    }

    public void startCounting() {
        counterTrasition.play();
    }


    @Override
    public RootPane getRootPane() {
        return rootPane;
    }
}
