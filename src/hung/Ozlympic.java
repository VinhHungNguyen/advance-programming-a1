package hung;

import hung.modules.home.HomePane;
import hung.utils.ViewUtils;
import hung.views.RootPane;
import hung.workers.GameWorker;
import hung.workers.ParticipantWorker;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class Ozlympic extends Application {

    private HomePane homePane;

    public Ozlympic() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ParticipantWorker.loadParticipants();
        GameWorker.loadGames();

        RootPane rootPane = new RootPane();

        homePane = new HomePane(rootPane);
        rootPane.add(homePane);

        Scene scene = new Scene(rootPane, ViewUtils.WINDOW_WIDTH, ViewUtils.WINDOW_HEIGHT);

        // Setup stage
        primaryStage.setTitle("Ozlympic");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

