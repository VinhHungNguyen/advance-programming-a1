package hung.utils;

import hung.views.RootPane;
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
     * Fade in a pane then add that pane to a parent pane
     * @param rootPane The root pane
     * @param pane The pane to fade in and add
     * @param fadeInTransition A preloading Fade transition used to perform the fade in animation
     * @return the fade transition performing the animation
     */
    public static FadeTransition fadeIn(RootPane rootPane, Pane pane, FadeTransition fadeInTransition) {
        fadeInTransition.setNode(pane);
        fadeInTransition.play();

        if (rootPane != null) {
            rootPane.add(pane);
        }
        return fadeInTransition;
    }

    /**
     * Fade in a pane then add that pane to a parent pane
     * @param rootPane The root pane
     * @param pane The pane to fade in and add
     * @param milSecDuration The duration of the animation in miliseconds
     * @return the fade transition performing the animation
     */
    public static FadeTransition fadeIn(RootPane rootPane, Pane pane, double milSecDuration) {
        FadeTransition fadeInTransition = makeFadeInTransition(milSecDuration);
        fadeIn(rootPane, pane, fadeInTransition);
        return fadeInTransition;
    }

    /**
     * Fade out a pane then remove that pane from its parent pane
     * @param rootPane The root pane
     * @param pane The pane to fade out and remove
     * @param fadeOutTransition A preloading Fade transition used to perform the fade out animation
     * @return the fade transition performing the animation
     */
    public static FadeTransition fadeOut(RootPane rootPane, Pane pane, FadeTransition fadeOutTransition) {
        if (rootPane != null) {
            fadeOutTransition.setOnFinished(event1 -> {
                rootPane.getChildren().remove(pane);
            });
        }

        fadeOutTransition.setNode(pane);
        fadeOutTransition.play();
        return fadeOutTransition;
    }

    /**
     * Fade out a pane then remove that pane from its parent pane
     * @param rootPane The root pane
     * @param pane The pane to fade out and remove
     * @param milSecDuration The duration of the animation in miliseconds
     * @return the fade transition performing the animation
     */
    public static FadeTransition fadeOut(RootPane rootPane, Pane pane, double milSecDuration) {
        FadeTransition fadeOutTransition = makeFadeOutTransition(milSecDuration);
        fadeOut(rootPane, pane, fadeOutTransition);
        return fadeOutTransition;
    }

    /**
     * Make a fade transition
     * @param milSecDuration The duration of the animation in miliseconds
     * @param fromValue Starting alpha value
     * @param toValue Ending alpha value
     * @return The fade transition
     */
    public static FadeTransition makeFadeTransition(double milSecDuration, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(milSecDuration));
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(1);
        return fadeTransition;
    }

    public static FadeTransition makeFadeInTransition(double milSecDuration) {
        return makeFadeTransition(milSecDuration, 0.0d, 1.0d);
    }

    public static FadeTransition makeFadeOutTransition(double milSecDuration) {
        return makeFadeTransition(milSecDuration, 1.0d, 0.0d);
    }

    /**
     * Bind the size of a pane to another so that they always have equal sizes.
     * @param equalizedPane The binding pane
     * @param targetPane THe bindable pane
     */
    public static void equalizePane(Pane equalizedPane, Pane targetPane) {
        equalizedPane.prefWidthProperty().bind(targetPane.widthProperty());
        equalizedPane.prefHeightProperty().bind(targetPane.heightProperty());
    }
}
