package hung.modules.listview;

import hung.views.RootPane;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ListViewPane extends VBox implements RootPane.Helper {

    private RootPane rootPane;

    private Label titleLabel;
    private RowView headerView;
    private ListView listView;
    private Button continueButton;

    private String title;

    private ListViewViewModel viewModel;
    private ListViewRouter router;

    public ListViewPane(RootPane rootPane, ListViewViewModel viewModel, String title) {
        this.rootPane = rootPane;
        this.viewModel = viewModel;
        this.title = title;
        router = new ListViewRouter();

        setupListView();
        setupButtonAndTitleView();

        setStyle("-fx-background-color: rgba(0,0,255);");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(0, 64, 0, 64));
        setMargin(titleLabel, new Insets(0, 0, 32, 0));
        setMargin(continueButton, new Insets(32, 0, 0, 0));
        getChildren().addAll(titleLabel, headerView, listView, continueButton);
    }

    private void setupListView() {
        listView = new ListView();
        listView.prefWidthProperty().bind(widthProperty().multiply(0.75));
        listView.prefHeightProperty().bind(heightProperty().divide(2));
        listView.setPadding(new Insets(0, 0, 0, 0));
        listView.setItems(viewModel.getRowViewModels());

        listView.setCellFactory(param -> new ListCell<ListViewViewModel.RowViewModel>() {
            @Override
            protected void updateItem(ListViewViewModel.RowViewModel item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(new Pane());
                } else {
                    setGraphic(new RowView(item));
                }

                setPadding(new Insets(0, 0, 0, 0));
            }
        });

        headerView = new RowView(viewModel.getHeaderViewModel());
    }

    private void setupButtonAndTitleView() {
        titleLabel = new Label(title);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle(
                "-fx-font-size: 40px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: rgba(255,255,255);");

        continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 24px;");
        continueButton.setOnAction(event -> {
            router.backToMainMenu(this);
        });
    }

    @Override
    public RootPane getRootPane() {
        return rootPane;
    }

    private class RowView extends HBox {

        ListViewViewModel.RowViewModel rowViewModel;

        public RowView(ListViewViewModel.RowViewModel rowViewModel) {
            this.rowViewModel = rowViewModel;
            String textColorStyleString = rowViewModel.isHeader()
                    ? "-fx-text-fill: rgba(255,255,255); -fx-background-color: rgba(0,0,0);"
                    : "-fx-text-fill: rgba(0,0,0); -fx-background-color: rgba(255,255,255);";
            String backgroundColorStyleString = rowViewModel.isHeader() ?
                    "-fx-background-color: rgba(255,255,255);" : "-fx-background-color: rgba(0,0,0);";

            int numColumns = rowViewModel.getColumns().size();
            for (int i = 0; i < numColumns; i++) {
                String text = rowViewModel.getColumns().get(i);
                Label label = new Label(text);
                label.prefWidthProperty().bind(widthProperty().divide(numColumns));
                label.setPrefHeight(48);
                label.setAlignment(Pos.CENTER);
                label.setStyle(
                        "-fx-font-size: 16px; " +
                                "-fx-font-weight: bold; " +
                                textColorStyleString);

                getChildren().add(label);
            }

            setSpacing(2);
            setPadding(new Insets(0, 0, 0, 0));
            prefWidthProperty().bind(listView.widthProperty());
            setStyle(backgroundColorStyleString);
        }
    }
}
