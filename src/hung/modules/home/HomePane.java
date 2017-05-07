package hung.modules.home;

import hung.utils.ViewUtils;
import hung.views.RootPane;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class HomePane extends Pane implements RootPane.Helper {

    private static final int menuWidth = 160;
    private static final int menuHeight = 48;
    private static final int menuTranslateDistance = 300;

//    private static final int listViewWidth = 160;

    private RootPane rootPane;

    private VBox menuPane;
    private Button newGameButton;
    private Button historyButton;
    private Button leaderboardButton;

    private TranslateTransition menuTransition;
    private boolean menuTransitionReversed;

    private HomeViewModel viewModel;
    private HomeRouter router;

    public HomePane(RootPane rootPane) {
        super();

        this.rootPane = rootPane;
        viewModel = new HomeViewModel();
        router = new HomeRouter();

        setupMenuView();

        getChildren().addAll(menuPane);
        setupMenuTransition();

        setStyle("-fx-background-color: rgba(255,255,255);");
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
        router.toNewGame(this, viewModel);
    }

    /**
     * Handle History button click
     */
    private void historyButtonClicked() {
        router.toHistory(this);
    }

    /**
     * Handle Leaderboard button click
     */
    private void leaderboardButtonClicked() {
        router.toLeaderboard(this);
    }

    @Override
    public RootPane getRootPane() {
        return rootPane;
    }
}
