package hung.models;

/**
 * Created by hungnguyen on 4/1/17.
 */
public abstract class Athlete extends Participant {

    public static final String TYPE_OFFICER = "officer";
    public static final String TYPE_SPRINTER = "sprinter";
    public static final String TYPE_SUPER = "super";
    public static final String TYPE_SWIMMER = "swimmer";
    public static final String TYPE_CYCLIST = "cyclist";

    protected Game game;

    protected int totalPoint;
    protected int previousReceivedPoint;
    protected int previousAchieveTime;

    public Athlete(String id, String name, String state, int age) {
        super(id, name, state, age);
//        System.out.println(this);
    }

    /**
     * Randomly generate a time which the athlete finishes a game.
     * The range of random time depends on the sport this athlete plays.
     */
    public abstract int compete();

    /**
     * Get the ID prefix of the game which this athlete can participate
     * @return The ID prefix of the game
     */
    public abstract String getPlayableGameIdPrefix();

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getTotalPoint() {
        return totalPoint;
    }

    public int getPreviousReceivedPoint() {
        return previousReceivedPoint;
    }

    public void setPreviousReceivedPoint(int previousReceivedPoint) {
        this.previousReceivedPoint = previousReceivedPoint;
        totalPoint += previousReceivedPoint;
    }

    public int getPreviousAchieveTime() {
        return previousAchieveTime;
    }

    public void setPreviousAchieveTime(int previousAchieveTime) {
        this.previousAchieveTime = previousAchieveTime;
    }
}
