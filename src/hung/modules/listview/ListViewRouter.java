package hung.modules.listview;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ListViewRouter {

    public void backToMainMenu(ListViewPane listViewPane) {
        listViewPane.getRootPane().removeWithAnimation(listViewPane, null);
    }
}
