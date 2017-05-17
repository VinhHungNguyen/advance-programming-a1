package tests;

import hung.modules.listview.ListViewViewModel;
import hung.workers.ParticipantWorker;
import org.junit.Assert;
import org.junit.BeforeClass;

/**
 * Created by hungnguyen on 5/18/17.
 */
public class ListViewViewModelTest {

    private static ListViewViewModel viewModel;
    private static int numOfAthletes;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ParticipantWorker.loadParticipants();

        String[] headerContents = {"Athlete ID", "Name", "Type", "Points", "State"};
        String[][] rowContents = ParticipantWorker.getAllAthletesAsStrings();
        numOfAthletes = rowContents.length;

        viewModel = new ListViewViewModel(headerContents, rowContents);
    }

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @org.junit.Test
    public void getHeaderViewModel() throws Exception {
        Assert.assertEquals(5, viewModel.getHeaderViewModel().getColumns().size());
    }

    @org.junit.Test
    public void getRowViewModels() throws Exception {
        Assert.assertEquals(numOfAthletes, viewModel.getRowViewModels().size());
    }

}