package hung.views;

import hung.models.Athlete;
import hung.models.Official;
import hung.models.Participant;
import hung.utils.ViewUtils;
import hung.viewmodels.MainViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

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

        // Setup the list views for game types, officers, and athletes
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

        // The container grouping all list views
        HBox listViewContainer = new HBox();
//        listViewContainer.setStyle("-fx-background-color: #000000;");
        listViewContainer.setAlignment(Pos.CENTER);
        listViewContainer.setPadding(new Insets(10, 10, 10, 10));
        listViewContainer.setSpacing(10);
        listViewContainer.getChildren().addAll(gameTypeListView, officerListView, athleteListView);


        // The Ok button to enter a game
        Button okButton = new Button(" Ok ");
        okButton.setOnAction(event -> {

        });

        // The Back button to exit the New Game scene
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> {
            ViewUtils.fadeOut(this, parentPane, 500);
        });

        // The bottom bar containing Back and Ok buttons
        BorderPane bottomBar = new BorderPane();
        bottomBar.prefWidthProperty().bind(listViewContainer.widthProperty());
        bottomBar.setPadding(new Insets(10, 10, 10, 10));
        bottomBar.setLeft(backButton);
        bottomBar.setRight(okButton);

        prefWidthProperty().bind(parentPane.widthProperty());
        prefHeightProperty().bind(parentPane.heightProperty());

        setOrientation(Orientation.VERTICAL);
        setStyle("-fx-background-color: #99999999;");
        setAlignment(Pos.CENTER);
        getChildren().addAll(listViewContainer, bottomBar);
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
