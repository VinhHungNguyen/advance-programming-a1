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

    public static void fadeIn(FadeTransition fadeInTransition, Pane pane, Pane parentPane) {
        fadeInTransition.setNode(pane);
        fadeInTransition.play();

        if (parentPane != null) {
            parentPane.getChildren().add(pane);
        }
    }

    public static void fadeIn(Pane pane, Pane parentPane, double milSecDuration) {
        FadeTransition fadeInTransition = makeFadeTransition(milSecDuration, 0.0d, 1.0d);
        fadeIn(fadeInTransition, pane, parentPane);
    }

    /**
     * Fade out a pane then remove that pane from its parent pane
     * @param fadeOutTransition A preloading Fade transition used to perform the fade out animation
     * @param pane The pane to fade out and remove
     * @param parentPane The parent pane
     */
    public static void fadeOut(FadeTransition fadeOutTransition, Pane pane, Pane parentPane) {
        if (parentPane != null) {
            fadeOutTransition.setOnFinished(event1 -> {
                parentPane.getChildren().remove(pane);
            });
        }

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
        FadeTransition fadeOutTransition = makeFadeTransition(milSecDuration, 1.0d, 0.0d);
        fadeOut(fadeOutTransition, pane, parentPane);
    }

    public static FadeTransition makeFadeTransition(double milSecDuration, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(milSecDuration));
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(1);
        return fadeTransition;
    }
}
