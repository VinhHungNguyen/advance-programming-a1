package hung;

import hung.utils.ViewUtils;
import hung.views.MainPane;
import hung.views.RootPane;
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
        String name = Thread.currentThread().getName();
        System.out.println("start() method: " + name);

        RootPane rootPane = new RootPane();

        mainPane = new MainPane(rootPane);
        rootPane.add(mainPane);

        Scene scene = new Scene(rootPane, ViewUtils.WINDOW_WIDTH, ViewUtils.WINDOW_HEIGHT);

        // Setup stage
        primaryStage.setTitle("Ozlympic");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

