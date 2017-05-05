package hung.viewmodels;

import hung.models.Athlete;
import hung.models.Official;
import javafx.collections.ObservableList;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class GamePlayViewModel {

    private Official officer;
    private ObservableList<Athlete> athletes;
    private Athlete predictedAthlete;

    public GamePlayViewModel(Official officer, ObservableList<Athlete> athletes, Athlete predictedAthlete) {
        this.officer = officer;
        this.athletes = athletes;
        this.predictedAthlete = predictedAthlete;
    }
}
