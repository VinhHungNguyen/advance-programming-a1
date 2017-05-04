package hung;

import hung.utils.ViewUtils;
import hung.views.MainPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class Ozlympic2 extends Application {

    private MainPane mainPane;

    public Ozlympic2() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainPane = new MainPane();

        Scene scene = new Scene(mainPane, ViewUtils.WINDOW_WIDTH, ViewUtils.WINDOW_HEIGHT);

        // Setup stage
        primaryStage.setTitle("Ozlympic");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

