package hung.models;

import hung.workers.ParticipantWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Game {

    public static final int MIN_TIME_SWIMMING = 100;
    public static final int RANGE_TIME_SWIMMING = 100;
    public static final int MIN_TIME_CYCLING = 500;
    public static final int RANGE_TIME_CYCLING = 300;
    public static final int MIN_TIME_RUNNING = 10;
    public static final int RANGE_TIME_RUNNING = 10;

    public static final int MIN_PARTICIPANTS = 5;
    public static final int MAX_PARTICIPANTS = 8;

    public static final String ID_PREFIX_SWIMMING = "S";
    public static final String ID_PREFIX_CYCLING = "C";
    public static final String ID_PREFIX_RUNNING = "R";


    public static final String TYPE_SWIMMING = "Swimming";
    public static final String TYPE_CYCLING = "Cycling";
    public static final String TYPE_RUNNING = "Running";

    private List<String> athleteIds;
    private String officialId;

    private String id;
    private String predictedAthleteId;
    private String lastResult;
    private Calendar finishingDate;

    private boolean doneRunning;

    public Game(String id) {
        this.id = id;
        athleteIds = new ArrayList<>();
        doneRunning = false;
        predictedAthleteId = "";
    }

    public Game(List<String> athleteIds, String officialId, String id, Calendar finishingDate) {
        this.athleteIds = athleteIds;
        this.officialId = officialId;
        this.id = id;
        this.finishingDate = finishingDate;
    }

    public boolean run() {
        // The number of participating athleteIds is not enough
        if (!isReadyToPlay()) {
            System.out.println("The game requires at least " + MIN_PARTICIPANTS + " athleteIds to run.\n" +
                    "Game cancelled.\n");
            return false;
        }

        for (String id : athleteIds) {
            Athlete a = ParticipantWorker.getAthleteById(id);
            a.setGame(this);
            a.setPreviousAchieveTime(a.compete());
        }

        doneRunning = true;
        System.out.println("Game is running.\n");

        return true;
    }

    /**
     * Add new athlete to this game.
     * If the game has reached maximum number of athleteIds or the athlete cannot join the game, print an alert then
     * return false. Otherwise, add the athlete then return true
     * @param athlete The athlete joining this game
     * @return True if adding successfully
     */
    public boolean addAthlete(Athlete athlete) {
        // Already reached max number of participants
        if (athleteIds.size() >= MAX_PARTICIPANTS) {
            System.out.println("This game has reached maximum number of participants (8 participants).");
            return false;
        }

        // The athlete cannot play this game
        if (!athlete.getPlayableGameIdPrefix().isEmpty() && !id.startsWith(athlete.getPlayableGameIdPrefix())) {
            System.out.println("This athlete cannot participate this game.");
            return false;
        }

        // The athlete is already added to this game
        for (String id : athleteIds) {
            if (id == athlete.getId()) {
                System.out.println("This athlete is already in this game.");
                return false;
            }
        }

//        athlete.setGame(this);
        athleteIds.add(athlete.getId());
        return true;
    }

    /**
     * Add all athleteIds from a given list of athleteIds
     * @param athletes The list or array of athleteIds
     * @return True if adding successfully
     */
    public boolean addAllAthlete(Athlete... athletes) {
        for (Athlete a : athletes) {
            if (!addAthlete(a)) {
                return false;
            }
        }
        return true;
    }

    public void handlePrediction(Athlete predictedAthlete) {
        this.predictedAthleteId = predictedAthlete.getId();
        finishingDate = Calendar.getInstance();

        // Summarise the game
        Official official = ParticipantWorker.getOfficialById(officialId);
        official.summarise(this);

        Athlete champion = ParticipantWorker.getAthleteById(athleteIds.get(0));

        // Achieved time of the predicted athlete is equal to the 1st place athlete => the same place => the winner
        if (predictedAthlete.getPreviousAchieveTime() == champion.getPreviousAchieveTime()) {
            System.out.println("Congratulation! You have predicted the correct user.");
        } else { // Otherwise
            System.out.println("Your prediction is incorrect.");
        }

        reset();
    }

    public boolean displayResult() {
        if (lastResult == null || lastResult.isEmpty()) {
            return false;
        }

        System.out.println(lastResult);
        return true;
    }

//    public String getGameResult() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SS");
//        StringBuilder sb = new StringBuilder(id);
//        sb.append(", ").append(officialId.getId()).append(", ").append(sdf.format(finishingDate.getTime()));
//
//        for (Athlete a : athleteIds) {
//            sb.append("\n")
//                    .append(a.getId()).append(", ")
//                    .append(a.getPreviousAchieveTime()).append(", ")
//                    .append(a.getPreviousReceivedPoint());
//        }
//        return sb.toString();
//    }

    public boolean isReadyToPlay() {
        return athleteIds.size() >= MIN_PARTICIPANTS;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(id);
        for (String id : athleteIds) {
            Athlete a = ParticipantWorker.getAthleteById(id);
            sb.append("\n");
            sb.append(a.toString());
        }
        return sb.toString();
    }

    private void reset() {
        doneRunning = false;
        predictedAthleteId = "";
    }

    public String getId() {
        return id;
    }

    public List<String> getAthleteIds() {
        return athleteIds;
    }

    public String getOfficialId() {
        return officialId;
    }

    public void setOfficialId(String officialId) {
        this.officialId = officialId;
    }

    public String getPredictedAthleteId() {
        return predictedAthleteId;
    }

    public void setPredictedAthleteId(String predictedAthleteId) {
        this.predictedAthleteId = predictedAthleteId;
    }

    public boolean isDoneRunning() {
        return doneRunning;
    }

    public String getLastResult() {
        return lastResult;
    }

    public void setLastResult(String lastResult) {
        this.lastResult = lastResult;
    }

    public Calendar getFinishingDate() {
        return finishingDate;
    }
}
