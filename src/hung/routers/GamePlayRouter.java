package hung.routers;

import hung.utils.ViewUtils;
import hung.views.GamePlayPane;

/**
 * Created by hungnguyen on 5/5/17.
 */
public class GamePlayRouter {

    public void back(GamePlayPane gamePlayPane) {
        gamePlayPane.getRootPane().removeWithAnimation(gamePlayPane, null);
    }
}
