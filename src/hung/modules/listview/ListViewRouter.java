package hung.modules.listview;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ListViewRouter {

    /**
     * Move back to main menu
     * @param listViewPane
     */
    public void backToMainMenu(ListViewPane listViewPane) {
        listViewPane.getRootPane().removeWithAnimation(listViewPane, null);
    }
}
