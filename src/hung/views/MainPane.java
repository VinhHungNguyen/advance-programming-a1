package hung.views;

import hung.models.Athlete;
import hung.models.Official;
import hung.models.Participant;
import hung.utils.ViewUtils;
import hung.viewmodels.MainViewModel;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
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

    private static final int listViewWidth = 160;

    private VBox menuPane;
    private Button newGameButton;
    private Button historyButton;
    private Button leaderboardButton;

    private FlowPane newGamePane;
    private ListView<String> gameTypeListView;
    private ListView<Official> officerListView;
    private ListView<Athlete> athleteListView;

    private FadeTransition fadeInTransition;
    private FadeTransition fadeOutTransition;
    private TranslateTransition menuTransition;
    private boolean menuTransitionReversed;

    private MainViewModel viewModel;

    public MainPane() {
        super();

        viewModel = new MainViewModel();

        setupMenuView();
        setupNewGamePane();
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

        newGameButton.setPrefWidth(menuWidth);
        newGameButton.setPrefHeight(menuHeight);
        historyButton.setPrefWidth(menuWidth);
        historyButton.setPrefHeight(menuHeight);
        leaderboardButton.setPrefWidth(menuWidth);
        leaderboardButton.setPrefHeight(menuHeight);

        newGameButton.setOnAction(event -> {
            newGameButtonClicked();
        });

        menuPane = new VBox();
        menuPane.getChildren().addAll(newGameButton, historyButton, leaderboardButton);
        menuPane.setLayoutX((ViewUtils.WINDOW_WIDTH - menuWidth) / 2);
        menuPane.setLayoutY(ViewUtils.WINDOW_HEIGHT / 2);
        menuPane.setStyle("-fx-background-color: #FFFFFF;");
    }

    /**
     * Setup the New Game view
     */
    private void setupNewGamePane() {
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            fadeOutTransition.setOnFinished(event1 -> {
                getChildren().remove(newGamePane);
            });

            fadeOutTransition.setNode(newGamePane);
            fadeOutTransition.play();
        });

        gameTypeListView = new ListView<>();
        officerListView = new ListView<>();
        athleteListView = new ListView<>();

        gameTypeListView.setItems(viewModel.getGameTypes());
        officerListView.setItems(viewModel.getOfficials());

        gameTypeListView.setPrefWidth(listViewWidth);
        gameTypeListView.setPrefHeight(listViewWidth);
        officerListView.setPrefWidth(listViewWidth);
        officerListView.setPrefHeight(listViewWidth);
        athleteListView.setPrefWidth(listViewWidth);
        athleteListView.setPrefHeight(listViewWidth);

        setParticipantCellFactory(officerListView);
        setParticipantCellFactory(athleteListView);

        newGamePane = new FlowPane();
        newGamePane.prefWidthProperty().bind(widthProperty());
        newGamePane.prefHeightProperty().bind(heightProperty());
        newGamePane.setStyle("-fx-background-color: #99999999;");
        newGamePane.setAlignment(Pos.CENTER);
        newGamePane.getChildren().addAll(backButton, gameTypeListView, officerListView, athleteListView);
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

        fadeOutTransition = new FadeTransition();
        fadeOutTransition.setDuration(Duration.millis(500));
        fadeOutTransition.setFromValue(1.0d);
        fadeOutTransition.setToValue(0.0d);
        fadeOutTransition.setCycleCount(1);
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
//        menuTransition.play();
        fadeInTransition.setNode(newGamePane);
        fadeInTransition.play();
        getChildren().add(newGamePane);
    }

    /**
     * Setup cell factory for list view with Participant type
     * @param listView The list view to set cell factory
     * @param <T> Generic type extending Participant
     */
    private <T extends Participant> void setParticipantCellFactory(ListView<T> listView) {
        listView.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.getName());
                }
            }
        });
    }
}
