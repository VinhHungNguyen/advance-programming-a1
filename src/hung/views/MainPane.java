package hung.views;

import hung.utils.ViewUtils;
import hung.viewmodels.MainViewModel;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class MainPane extends Pane {

    private static final int menuWidth = 160;
    private static final int menuHeight = 48;
    private static final int menuTranslateDistance = 300;

//    private static final int listViewWidth = 160;

    private VBox menuPane;
    private Button newGameButton;
    private Button historyButton;
    private Button leaderboardButton;

    private NewGamePane newGamePane;

    private FadeTransition fadeInTransition;
    private TranslateTransition menuTransition;
    private boolean menuTransitionReversed;

    private MainViewModel viewModel;

    public MainPane() {
        super();

        viewModel = new MainViewModel();

        setupMenuView();
        setupFadeTransition();

        getChildren().addAll(menuPane);
        setupMenuTransition();
    }

    /**
     * Setup the main menu
     */
    private void setupMenuView() {
        newGameButton = new Button("New Game");
        historyButton = new Button("History");
        leaderboardButton = new Button("Leaderboard");

        // Set buttons's sizes
        newGameButton.setPrefWidth(menuWidth);
        newGameButton.setPrefHeight(menuHeight);
        historyButton.setPrefWidth(menuWidth);
        historyButton.setPrefHeight(menuHeight);
        leaderboardButton.setPrefWidth(menuWidth);
        leaderboardButton.setPrefHeight(menuHeight);

        newGameButton.setOnAction(event -> {
            newGameButtonClicked();
        });

        historyButton.setOnAction(event -> {
            historyButtonClicked();
        });

        leaderboardButton.setOnAction(event -> {
            leaderboardButtonClicked();
        });

        menuPane = new VBox();
        menuPane.getChildren().addAll(newGameButton, historyButton, leaderboardButton);
        menuPane.setLayoutX((ViewUtils.WINDOW_WIDTH - menuWidth) / 2);
        menuPane.setLayoutY(ViewUtils.WINDOW_HEIGHT / 2);
        menuPane.setStyle("-fx-background-color: #FFFFFF;");
    }

    /**
     * Setup fade transition
     */
    private void setupFadeTransition() {
        fadeInTransition = new FadeTransition();
        fadeInTransition.setDuration(Duration.millis(500));
        fadeInTransition.setFromValue(0.0d);
        fadeInTransition.setToValue(1.0d);
        fadeInTransition.setCycleCount(1);
    }

    /**
     * Setup menu transition
     */
    private void setupMenuTransition() {
        menuTransitionReversed = false;

        menuTransition = new TranslateTransition();
        menuTransition.setDuration(Duration.millis(300));
        menuTransition.setNode(menuPane);
        menuTransition.setByX(-menuTranslateDistance);
        menuTransition.setToY(0);
        menuTransition.setCycleCount(1);

        menuTransition.setOnFinished(event -> {
            menuTransitionReversed = !menuTransitionReversed;

            if (menuTransitionReversed) {
                menuTransition.setByX(menuTranslateDistance);
            } else {
                menuTransition.setByX(-menuTranslateDistance);
            }
        });
    }

    /**
     * Handle New Game button click
     */
    private void newGameButtonClicked() {
        if (newGamePane == null) {
            newGamePane = new NewGamePane(this, viewModel);
        }

//        menuTransition.play();
        fadeInTransition.setNode(newGamePane);
        fadeInTransition.play();
        getChildren().add(newGamePane);
    }

    /**
     * Handle History button click
     */
    private void historyButtonClicked() {
    }

    /**
     * Handle Leaderboard button click
     */
    private void leaderboardButtonClicked() {
    }
}
