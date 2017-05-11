package hung.modules.listview;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by hungnguyen on 5/7/17.
 */
public class ListViewViewModel {

    private RowViewModel headerViewModel;
    private ObservableList<RowViewModel> rowViewModels;

    public ListViewViewModel(String[] headerContents, String[][] rowContents) {
        headerViewModel = new RowViewModel(true, headerContents);
        rowViewModels = FXCollections.observableArrayList();

        for (String[] rowContent : rowContents) {
            rowViewModels.add(new RowViewModel(false, rowContent));
        }
    }

    public RowViewModel getHeaderViewModel() {
        return headerViewModel;
    }

    public ObservableList<RowViewModel> getRowViewModels() {
        return rowViewModels;
    }

    class RowViewModel {
        private boolean isHeader;
        private ObservableList<String> columns;

        public RowViewModel(boolean isHeader, String... items) {
            this.isHeader = isHeader;
            columns = FXCollections.observableArrayList(items);
        }

        public ObservableList<String> getColumns() {
            return columns;
        }

        public boolean isHeader() {
            return isHeader;
        }
    }
}
