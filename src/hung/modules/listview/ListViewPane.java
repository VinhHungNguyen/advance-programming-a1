package hung.modules.listview;

import hung.views.RootPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ListViewPane extends VBox implements RootPane.Helper {

    private RootPane rootPane;

    private Label titleLabel;
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
        getChildren().addAll(titleLabel, listView, continueButton);
    }

    private void setupListView() {
        listView = new ListView();
        listView.prefWidthProperty().bind(widthProperty().multiply(0.75));
        listView.prefHeightProperty().bind(heightProperty().divide(2));
    }

    private void setupButtonAndTitleView() {
        titleLabel = new Label(title);
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
