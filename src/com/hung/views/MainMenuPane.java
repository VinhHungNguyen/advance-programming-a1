package com.hung.views;

import com.hung.utils.ViewUtils;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.*;
import javafx.util.Duration;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class MainMenuPane extends Pane {

    private static final int menuWidth = 160;
    private static final int menuHeight = 48;

    private VBox menuPane;
    private Button newGameButton;
    private Button historyButton;
    private Button leaderboardButton;

    private TranslateTransition menuTransition;
    private boolean menuTransitionReversed;

    public MainMenuPane() {
        super();

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
            menuTransition.play();
        });

        menuPane = new VBox();
        menuPane.getChildren().addAll(newGameButton, historyButton, leaderboardButton);
        menuPane.setLayoutX((ViewUtils.WINDOW_WIDTH - menuWidth) / 2);
        menuPane.setLayoutY(ViewUtils.WINDOW_HEIGHT / 2);
        menuPane.setStyle("-fx-background-color: #FFFFFF;");

        getChildren().add(menuPane);
        setupMenuTransition();
    }

    private void setupMenuTransition() {
        menuTransitionReversed = false;

        menuTransition = new TranslateTransition();
        menuTransition.setDuration(Duration.millis(500));
        menuTransition.setNode(menuPane);
        menuTransition.setByX(-300);
        menuTransition.setToY(0);
        menuTransition.setCycleCount(1);

        menuTransition.setOnFinished(event -> {
            menuTransitionReversed = !menuTransitionReversed;

            if (menuTransitionReversed) {
                menuTransition.setByX(300);
            } else {
                menuTransition.setByX(-300);
            }
        });
    }
}
