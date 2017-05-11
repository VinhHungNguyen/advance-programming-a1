package hung.modules.gameplay;

import hung.models.Athlete;
import hung.models.Game;
import hung.models.Official;
import hung.workers.GameWorker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class GamePlayViewModel {

    private Official officer;
    private ObservableList<Athlete> athletes;
    private Athlete predictedAthlete;

    private Game game;

    private StringProperty counterString;
    private int counter;

    public GamePlayViewModel(Official officer, ObservableList<Athlete> athletes, Athlete predictedAthlete) {
        this.officer = officer;
        this.athletes = athletes;
        this.predictedAthlete = predictedAthlete;

        List<String> athleteIds = new ArrayList<>();
        for (Athlete a : athletes) {
            athleteIds.add(a.getId());
        }

        String idPrefix = athletes.get(0).getPlayableGameIdPrefix();
        game = GameWorker.makeNewGame(idPrefix, officer.getId(), athleteIds, predictedAthlete.getId());

        counter = 3;
        counterString = new SimpleStringProperty(counter + "");
    }

    public void generateResult() {
        game.run();
    }

    public void summarise() {
        officer.summarise(game);
    }

    public void nextCount() {
        counter--;
        counterString.setValue(counter + "");
    }

    public String[][] getResultAsStrings() {
        String[][] results = new String[athletes.size()][];

        for (int i = 0; i < results.length; i++) {
            Athlete a = athletes.get(i);
            results[i] = new String[] {
                    a.getId(), a.getName(), a.getPreviousAchieveTime() + "", a.getPreviousReceivedPoint() + ""
            };
        }

        return results;
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
