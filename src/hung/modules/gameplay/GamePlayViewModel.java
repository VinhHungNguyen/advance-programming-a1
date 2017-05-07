package hung.modules.gameplay;

import hung.models.Athlete;
import hung.models.Official;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class GamePlayViewModel {

    private Official officer;
    private ObservableList<Athlete> athletes;
    private Athlete predictedAthlete;

    private StringProperty counterString;
    private int counter;

    public GamePlayViewModel(Official officer, ObservableList<Athlete> athletes, Athlete predictedAthlete) {
        this.officer = officer;
        this.athletes = athletes;
        this.predictedAthlete = predictedAthlete;

        counter = 3;
        counterString = new SimpleStringProperty(counter + "");
    }

    public void nextCount() {
        counter--;
        counterString.setValue(counter + "");
    }

    public String getCounterString() {
        return counterString.get();
    }

    public StringProperty counterStringProperty() {
        return counterString;
    }

    public int getCounter() {
        return counter;
    }
}
