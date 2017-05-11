package hung.models;

import hung.workers.ParticipantWorker;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hungnguyen on 4/2/17.
 */
public class Official extends Participant {

    public static final int FIRST_PLACE_REWARD = 5;
    public static final int SECOND_PLACE_REWARD = 2;
    public static final int THIRD_PLACE_REWARD = 1;

    public Official(String id, String name, String state, int age) {
        super(id, name, state, age);
    }

    /**
     * Sorting input list of athletes according to their achieved time, then display result.
     * @param game Input list of athletes
     */
    public void summarise(Game game) {
        List<String> athletes = game.getAthleteIds();

        // Sorting the list of athletes according to their achieved time
        Collections.sort(game.getAthleteIds(),  new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                Athlete a1 = ParticipantWorker.getAthleteById(o1);
                Athlete a2 = ParticipantWorker.getAthleteById(o2);
                if (a1.getPreviousAchieveTime() > a2.getPreviousAchieveTime()) {
                    return 1;
                } else if (a1.getPreviousAchieveTime() < a2.getPreviousAchieveTime()) {
                    return -1;
                }
                return 0;
            }
        });

        // display result, also save the result as last result of the game
//        StringBuilder resultSb = new StringBuilder(game.getId());
//        resultSb.append("'s result:").append("\n");
//
//        System.out.println("Result:");

        for (int i = 0; i < athletes.size(); i++) {
            Athlete a = ParticipantWorker.getAthleteById(athletes.get(i));
            a.setPreviousReceivedPoint(0);
//            StringBuilder sb = new StringBuilder("#");
//            sb.append(i + 1).append(" ").append(a.getName()).append(" - ID: ")
//                    .append(a.getId()).append(" - Time: ").append(a.getPreviousAchieveTime());
//
//            String result = sb.toString();
//            resultSb.append(result).append("\n");
//            System.out.println(result);
        }



        // Set rewards for top 3 athletes
        Athlete firstPlaceAthlete = ParticipantWorker.getAthleteById(athletes.get(0));
        Athlete secondPlaceAthlete = ParticipantWorker.getAthleteById(athletes.get(1));
        Athlete thirdPlaceAthlete = ParticipantWorker.getAthleteById(athletes.get(2));
        firstPlaceAthlete.setPreviousReceivedPoint(FIRST_PLACE_REWARD);
        secondPlaceAthlete.setPreviousReceivedPoint(SECOND_PLACE_REWARD);
        thirdPlaceAthlete.setPreviousReceivedPoint(THIRD_PLACE_REWARD);


//        game.setLastResult(resultSb.toString());

//        System.out.println();

        game.setFinishingDate(Calendar.getInstance().getTime());
    }
}
