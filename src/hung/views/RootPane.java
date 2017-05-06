package hung.views;

import hung.utils.ViewUtils;
import javafx.animation.FadeTransition;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * Created by hungnguyen on 5/5/17.
 */
public class RootPane extends StackPane {

    private FadeTransition fadeOutTransition;
    private FadeTransition fadeInTransition;
    private Pane overlay;

    public RootPane() {
        fadeOutTransition = ViewUtils.makeFadeOutTransition(500);
        fadeInTransition = ViewUtils.makeFadeInTransition(500);

        overlay = new Pane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0);");
    }

    /**
     * Add a pane to the stack, also resize it to fill the root pane
     * @param pane
     */
    public void add(Pane pane) {
        getChildren().add(pane);
        ViewUtils.equalizePane(pane, this);
    }

    /**
     * Add a pane to the stack with animation
     * @param pane
     */
    public void addWithAnimation(Pane pane, OnPaneTransitionFinishListener listener) {
        fadeInTransition.setOnFinished(event -> {
            fadeOutTransition.setOnFinished(event1 -> {
                getChildren().remove(overlay);
                fadeInTransition.setOnFinished(null);
                fadeOutTransition.setOnFinished(null);

                if (listener != null) {
                    listener.onFinished();
                }
            });

            fadeOutTransition.setNode(overlay);
            fadeOutTransition.play();

            getChildren().add(getChildren().size() - 1, pane);
        });

        addOverlay();
    }

    /**
     * Remove a pane from the stack with animation
     * @param pane
     */
    public void removeWithAnimation(Pane pane, OnPaneTransitionFinishListener listener) {
        fadeInTransition.setOnFinished(event -> {
            fadeOutTransition.setOnFinished(event1 -> {
                getChildren().remove(overlay);
                fadeInTransition.setOnFinished(null);
                fadeOutTransition.setOnFinished(null);

                if (listener != null) {
                    listener.onFinished();
                }
            });

            fadeOutTransition.setNode(overlay);
            fadeOutTransition.play();

            getChildren().remove(pane);
        });

        addOverlay();
    }

    private void addOverlay() {
        fadeInTransition.setNode(overlay);
        fadeInTransition.play();
        add(overlay);
    }

    public interface Helper {
        RootPane getRootPane();
    }

    public interface OnPaneTransitionFinishListener {
        void onFinished();
    }
}
