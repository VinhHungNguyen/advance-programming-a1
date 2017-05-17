package tests;

import hung.models.Athlete;
import hung.models.Game;
import hung.models.Swimmer;
import hung.modules.newgame.NewGameViewModel;
import hung.workers.ParticipantWorker;
import javafx.collections.ObservableList;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by hungnguyen on 5/18/17.
 */
public class NewGameViewModelTest {

    private static NewGameViewModel viewModel;

    @BeforeClass
    public static void setUpClass() throws Exception {
        ParticipantWorker.loadParticipants();
        viewModel = new NewGameViewModel();
    }

    @Test
    public void updateAthletesByType() throws Exception {
        ObservableList<Athlete> athletes = viewModel.updateAthletesByType(Game.TYPE_SWIMMING);
        Assert.assertTrue(athletes.get(0) instanceof Swimmer);
    }

    @Test
    public void setSelectedOfficer() throws Exception {
        viewModel.setSelectedOfficer(null);
        Assert.assertTrue(viewModel.getOkDisabled() == true);
    }

    @Test
    public void setPredictedAthlete() throws Exception {
        viewModel.setPredictedAthlete(null);
        Assert.assertTrue(viewModel.getOkDisabled() == true);
    }

}