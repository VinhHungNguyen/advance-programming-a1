package hung.views;

import hung.models.Athlete;
import hung.models.Official;
import hung.models.Participant;
import hung.utils.ViewUtils;
import hung.viewmodels.MainViewModel;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class NewGamePane extends FlowPane {

    private static final int listViewWidth = 160;

    private ListView<String> gameTypeListView;
    private ListView<Official> officerListView;
    private ListView<Athlete> athleteListView;
    private ListView<Athlete> selectedAthleteListView;

    private MainViewModel viewModel;

    public NewGamePane(Pane parentPane, MainViewModel viewModel) {
        this.viewModel = viewModel;

        GridPane listViewContainer = setupListViews();

        // Setup instruction label
        Label instructionLabel =
                new Label("Hold Ctrl (on Window) or Command (on Mac) + left click\nto select multiple athletes");
        setupLabelStyle(instructionLabel);
        instructionLabel.prefWidthProperty().bind(listViewContainer.widthProperty());

        BorderPane bottomBar = setupBottomBar();
        bottomBar.prefWidthProperty().bind(listViewContainer.widthProperty());


        // Setup this pane's attributes
        prefWidthProperty().bind(parentPane.widthProperty());
        prefHeightProperty().bind(parentPane.heightProperty());
        setOrientation(Orientation.VERTICAL);
        setStyle("-fx-background-color: rgba(0,0,0,0.6);");
        setAlignment(Pos.CENTER);
        getChildren().addAll(listViewContainer, instructionLabel, bottomBar);
    }

    /**
     * Setup the list views for game types, officers, and athletes
     * @return The pane containing all of the list views
     */
    private GridPane setupListViews() {
        // Setup the list views
        gameTypeListView = new ListView<>();
        officerListView = new ListView<>();
        athleteListView = new ListView<>();
        selectedAthleteListView = new ListView<>();

        gameTypeListView.setItems(viewModel.getGameTypes());
        officerListView.setItems(viewModel.getOfficials());
        athleteListView.setItems(viewModel.getAthletesOfType());
        selectedAthleteListView.setItems(viewModel.getSelectedAthletes());

        gameTypeListView.setPrefWidth(listViewWidth);
        gameTypeListView.setPrefHeight(listViewWidth);
        officerListView.setPrefWidth(listViewWidth);
        officerListView.setPrefHeight(listViewWidth);
        athleteListView.setPrefWidth(listViewWidth);
        athleteListView.setPrefHeight(listViewWidth);
        selectedAthleteListView.setPrefWidth(listViewWidth);
        selectedAthleteListView.setPrefHeight(listViewWidth);

        setParticipantCellFactory(officerListView);
        setParticipantCellFactory(athleteListView);
        setParticipantCellFactory(selectedAthleteListView);

        gameTypeListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && oldValue.equals(newValue)) {
                return;
            }

            viewModel.updateAthletesByType(newValue);
//            athleteListView.setItems(viewModel.updateAthletesByType(newValue));
        });

        athleteListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        athleteListView.setOnMouseClicked(event -> {
            ObservableList<Athlete> selectedAthletes = athleteListView.getSelectionModel().getSelectedItems();
            viewModel.updateSelectedAthletes(selectedAthletes);

//            System.out.print("Selected Athletes: ");
//            for (Athlete a : selectedAthletes) {
//                System.out.print(a.getName() + ", ");
//            }
//            System.out.println();
        });


        // Setup the headers
        Label gameTypeLabel = new Label("Types of Game");
        Label officerLabel = new Label("Oficers");
        Label athleteLabel = new Label("Athletes");
        Label predictionLabel = new Label("Your Prediction");

        setupLabelStyle(gameTypeLabel);
        setupLabelStyle(officerLabel);
        setupLabelStyle(athleteLabel);
        setupLabelStyle(predictionLabel);

        gameTypeLabel.setPrefWidth(listViewWidth);
        officerLabel.setPrefWidth(listViewWidth);
        athleteLabel.setPrefWidth(listViewWidth);
        predictionLabel.setPrefWidth(listViewWidth);


        // Setup the container grouping all list views
        GridPane listViewContainer = new GridPane();
        listViewContainer.setAlignment(Pos.CENTER);
        listViewContainer.setHgap(10);
        listViewContainer.setVgap(10);

        listViewContainer.add(gameTypeLabel, 0, 0);
        listViewContainer.add(officerLabel, 1, 0);
        listViewContainer.add(athleteLabel, 2, 0);
        listViewContainer.add(predictionLabel, 3, 0);

        listViewContainer.add(gameTypeListView, 0, 1);
        listViewContainer.add(officerListView, 1, 1);
        listViewContainer.add(athleteListView, 2, 1);
        listViewContainer.add(selectedAthleteListView, 3, 1);

        return listViewContainer;
    }

    /**
     * Setup the bottom bar
     * @return The pane representing the bottom bar
     */
    private BorderPane setupBottomBar() {
        // The Ok button to enter a game
        Button okButton = new Button(" Ok ");
        okButton.setOnAction(event -> {
            okButtonClicked();
        });

        // The Back button to exit the New Game scene
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            ViewUtils.fadeOut(this, (Pane) getParent(), 500);
        });

        // The bottom bar containing Back and Ok buttons
        BorderPane bottomBar = new BorderPane();
        bottomBar.setLeft(backButton);
        bottomBar.setRight(okButton);

        return bottomBar;
    }

    /**
     * Setup the style of a given label
     * @param label
     */
    private void setupLabelStyle(Label label) {
        label.setStyle("-fx-text-alignment: center; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: rgba(255,255,255);");
    }

    /**
     * Handle ok button clicked
     */
    private void okButtonClicked() {
        Official official = officerListView.getSelectionModel().getSelectedItem();
        ObservableList<Athlete> athletes = athleteListView.getSelectionModel().getSelectedItems();
        Athlete predictedAthlete = selectedAthleteListView.getSelectionModel().getSelectedItem();

//        selectedAthleteListView.setItems(athletes);

//        System.out.println("Selected Officer: " + official);
//        System.out.print("Selected Athletes: ");
//        for (Athlete a : athletes) {
//            System.out.print(a + ", ");
//        }
//        System.out.println();
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
