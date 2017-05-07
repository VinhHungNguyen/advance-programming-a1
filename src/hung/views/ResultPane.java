package hung.views;

import hung.routers.ResultRouter;
import hung.viewmodels.ResultViewModel;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ResultPane extends VBox implements RootPane.Helper {

    private RootPane rootPane;

    private Label titleLabel;
    private ListView listView;
    private Button continueButton;

    private ResultViewModel viewModel;
    private ResultRouter router;

    public ResultPane(RootPane rootPane, ResultViewModel viewModel) {
        this.rootPane = rootPane;
        this.viewModel = viewModel;
        router = new ResultRouter();

        setupListView();
        setupButtonAndTitleView();

        setStyle("-fx-background-color: rgba(0,0,255);");
        setAlignment(Pos.CENTER);
        setPadding(new Insets(0, 64, 0, 64));
        setMargin(titleLabel, new Insets(0, 0, 32, 0));
        setMargin(continueButton, new Insets(32, 0, 0, 0));
        getChildren().addAll(titleLabel, listView, continueButton);
    }

    private void setupListView() {
        listView = new ListView();
        listView.prefWidthProperty().bind(widthProperty().multiply(0.75));
        listView.prefHeightProperty().bind(heightProperty().divide(2));
    }

    private void setupButtonAndTitleView() {
        titleLabel = new Label("Result");
//        titleLabel.prefWidthProperty().bind(listView.prefWidthProperty());
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setStyle(
                "-fx-font-size: 48px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: rgba(255,255,255);");

        continueButton = new Button("Continue");
        continueButton.setStyle("-fx-font-size: 32px;");
        continueButton.setOnAction(event -> {
            router.backToMainMenu(this);
        });
    }

    @Override
    public RootPane getRootPane() {
        return rootPane;
    }
}
