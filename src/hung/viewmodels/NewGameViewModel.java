package hung.viewmodels;

import hung.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by hungnguyen on 5/4/17.
 */
public class NewGameViewModel {

    private ObservableList<Swimmer> swimmers;
    private ObservableList<Cyclist> cyclists;
    private ObservableList<Sprinter> sprinters;
    private ObservableList<SuperAthlete> superAthletes;

    private ObservableList<Official> officials;

    private ObservableList<String> gameTypes;
    private ObservableList<Athlete> athletesOfType;
    private ObservableList<Athlete> selectedAthletes;

    public NewGameViewModel(ObservableList<Swimmer> swimmers, ObservableList<Cyclist> cyclists,
                            ObservableList<Sprinter> sprinters, ObservableList<SuperAthlete> superAthletes,
                            ObservableList<Official> officials) {
        this.swimmers = swimmers;
        this.cyclists = cyclists;
        this.sprinters = sprinters;
        this.superAthletes = superAthletes;
        this.officials = officials;

        gameTypes = FXCollections.observableArrayList(Game.TYPE_SWIMMING, Game.TYPE_CYCLING, Game.TYPE_RUNNING);
        athletesOfType = FXCollections.observableArrayList();
        selectedAthletes = FXCollections.observableArrayList();
    }

    /**
     * Get all athletes who can play a given type of game.
     * @param type The given type of game
     * @return All athletes who can play that type of game
     */
    public ObservableList<Athlete> updateAthletesByType(String type) {
        selectedAthletes.clear();
        if (type.equals(Game.TYPE_SWIMMING)) {
            return getSwimmableAthletes();
        }
        if (type.equals(Game.TYPE_CYCLING)) {
            return getCyclableAthletes();
        }
        if (type.equals(Game.TYPE_RUNNING)) {
            return getRunnableAthletes();
        }
        return null;
    }

    /**
     * Get all athletes who can swim
     * @return All athletes who can swim
     */
    public ObservableList<Athlete> getSwimmableAthletes() {
        return getAthletesOfAGameType(swimmers);
    }

    /**
     * Get all athletes who can cycle
     * @return All athletes who can cycle
     */
    public ObservableList<Athlete> getCyclableAthletes() {
        return getAthletesOfAGameType(cyclists);
    }

    /**
     * Get all athletes who can run
     * @return All athletes who can run
     */
    public ObservableList<Athlete> getRunnableAthletes() {
        return getAthletesOfAGameType(sprinters);
    }

    /**
     * Get all athlete who can play the same type of game as the given list of athletes, including themselves
     * @param a The given list of athletes
     * @param <T> The type of the given athletes
     * @return All athlete who can play the same type of game as the given athletes
     */
    private <T extends Athlete> ObservableList<Athlete> getAthletesOfAGameType(ObservableList<T> a) {
        athletesOfType.clear();
        athletesOfType.addAll(a);
        athletesOfType.addAll(superAthletes);
        return athletesOfType;
    }

    public void updateSelectedAthletes(ObservableList<Athlete> selectedAthletes) {
        this.selectedAthletes.clear();
        this.selectedAthletes.addAll(selectedAthletes);
    }

    public ObservableList<Official> getOfficials() {
        return officials;
    }

    public ObservableList<String> getGameTypes() {
        return gameTypes;
    }

    public ObservableList<Athlete> getAthletesOfType() {
        return athletesOfType;
    }

    public ObservableList<Athlete> getSelectedAthletes() {
        return selectedAthletes;
    }
}
