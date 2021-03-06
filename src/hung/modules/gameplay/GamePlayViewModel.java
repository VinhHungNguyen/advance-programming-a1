package hung.modules.gameplay;

import hung.models.Athlete;
import hung.models.Game;
import hung.models.Official;
import hung.workers.GameWorker;
import hung.workers.ParticipantWorker;
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

    // The counter on the screen
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

    public void saveGame() {
        GameWorker.insertGame(game);
    }

    public void nextCount() {
        counter--;
        counterString.setValue(counter + "");
    }

    public String[][] getResultAsStrings() {
        String[][] results = new String[athletes.size()][];

        for (int i = 0; i < results.length; i++) {
            Athlete a = ParticipantWorker.getAthleteById(game.getAthleteIds().get(i));
            results[i] = new String[] {
                    a.getId(), a.getName(), a.getPreviousAchieveTime() + "", a.getPreviousReceivedPoint() + ""
            };
        }

        return results;
    }

    public boolean correctPrediction() {
        return predictedAthlete.getId().equalsIgnoreCase(game.getAthleteIds().get(0))
                || predictedAthlete.getId().equalsIgnoreCase(game.getAthleteIds().get(1))
                || predictedAthlete.getId().equalsIgnoreCase(game.getAthleteIds().get(2));
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

    public Athlete getPredictedAthlete() {
        return predictedAthlete;
    }
}
