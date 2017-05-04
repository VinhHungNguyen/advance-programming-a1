package hung.views;

import hung.models.Athlete;
import hung.models.Official;
import hung.models.Participant;
import hung.utils.ViewUtils;
import hung.viewmodels.MainViewModel;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.awt.*;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class NewGamePane extends FlowPane {

    private static final int listViewWidth = 160;

    private ListView<String> gameTypeListView;
    private ListView<Official> officerListView;
    private ListView<Athlete> athleteListView;

    private MainViewModel viewModel;

    public NewGamePane(Pane parentPane, MainViewModel viewModel) {
        this.viewModel = viewModel;

        setupListViews();

        // Setup the container grouping all list views
        HBox listViewContainer = new HBox();
//        listViewContainer.setStyle("-fx-background-color: #000000;");
        listViewContainer.setAlignment(Pos.CENTER);
        listViewContainer.setSpacing(10);
        listViewContainer.getChildren().addAll(gameTypeListView, officerListView, athleteListView);

        // Setup instruction label
        Label instructionLabel =
                new Label("Hold Ctrl (on Window) or Command (on Mac) + left click\nto select multiple athletes");
//        instructionLabel.setTextFill(Color.WHITE);
//        instructionLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        instructionLabel.setStyle(
                "-fx-text-alignment: center; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: rgba(255,255,255);");


        // The Ok button to enter a game
        Button okButton = new Button(" Ok ");
        okButton.setOnAction(event -> {
            okButtonClicked();
        });

        // The Back button to exit the New Game scene
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            ViewUtils.fadeOut(this, parentPane, 500);
        });

        // The bottom bar containing Back and Ok buttons
        BorderPane bottomBar = new BorderPane();
        bottomBar.prefWidthProperty().bind(listViewContainer.widthProperty());
        bottomBar.setLeft(backButton);
        bottomBar.setRight(okButton);

        prefWidthProperty().bind(parentPane.widthProperty());
        prefHeightProperty().bind(parentPane.heightProperty());

        setOrientation(Orientation.VERTICAL);
        setStyle("-fx-background-color: rgba(0,0,0,0.5);");
        setAlignment(Pos.CENTER);
        getChildren().addAll(listViewContainer, instructionLabel, bottomBar);
    }

    /**
     * Setup the list views for game types, officers, and athletes
     */
    private void setupListViews() {
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


        gameTypeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && oldValue.equals(newValue)) {
                return;
            }

            athleteListView.setItems(viewModel.getAthletesByType(newValue));
        });

        athleteListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Handle ok button clicked
     */
    private void okButtonClicked() {
        Official official = officerListView.getSelectionModel().getSelectedItem();

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
