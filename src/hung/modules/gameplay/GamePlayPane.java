package hung.modules.gameplay;

import hung.utils.ViewUtils;
import hung.views.RootPane;
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

    // The counter on the screen
    private GamePlayViewModel viewModel;
    private GamePlayRouter router;

    public GamePlayPane(RootPane rootPane, GamePlayViewModel viewModel) {
        this.rootPane = rootPane;
        this.viewModel = viewModel;
        router = new GamePlayRouter();

        setupCounterView();
        setupAnimations();

        setStyle("-fx-background-color: rgba(0,255,0);");
    }

    /**
     * Setup the counter
     */
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

    /**
     * Setup animation
     */
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

            // If count to 1, move to Game Result
            if (viewModel.getCounter() == 1) {
                getChildren().remove(counterPane);

                viewModel.generateResult();
                viewModel.summarise();
                viewModel.saveGame();
                router.toGameResult(this, viewModel);

                return;
            }

            // Next count
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
