package hung.utils;

import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 * Created by hungnguyen on 5/1/17.
 */
public class ViewUtils {

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    /**
     * Fade out a pane then remove that pane from its parent pane
     * @param fadeOutTransition A preloading Fade transition used to perform the fade out animation
     * @param pane The pane to fade out and remove
     * @param parentPane The parent pane
     */
    public static void fadeOut(FadeTransition fadeOutTransition, Pane pane, Pane parentPane) {
        fadeOutTransition.setOnFinished(event1 -> {
            parentPane.getChildren().remove(pane);
        });

        fadeOutTransition.setNode(pane);
        fadeOutTransition.play();
    }

    /**
     * Fade out a pane then remove that pane from its parent pane
     * @param pane The pane to fade out and remove
     * @param parentPane The parent pane
     * @param milSecDuration The duration of the animation in miliseconds
     */
    public static void fadeOut(Pane pane, Pane parentPane, double milSecDuration) {
        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setDuration(Duration.millis(milSecDuration));
        fadeOutTransition.setFromValue(1.0d);
        fadeOutTransition.setToValue(0.0d);
        fadeOutTransition.setCycleCount(1);

        fadeOut(fadeOutTransition, pane, parentPane);
    }
}
