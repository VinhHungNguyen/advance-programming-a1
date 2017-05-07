package hung.routers;

import hung.views.ResultPane;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ResultRouter {

    public void backToMainMenu(ResultPane resultPane) {
        resultPane.getRootPane().removeWithAnimation(resultPane, null);
    }
}
