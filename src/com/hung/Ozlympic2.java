package com.hung;

import com.hung.utils.ViewUtils;
import com.hung.views.MainMenuPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class Ozlympic2 extends Application {

    private MainMenuPane mainMenuPane;


    public Ozlympic2() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainMenuPane = new MainMenuPane();

        Scene scene = new Scene(mainMenuPane, ViewUtils.WINDOW_WIDTH, ViewUtils.WINDOW_HEIGHT);

        // Setup stage
        primaryStage.setTitle("Ozlympic");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}

