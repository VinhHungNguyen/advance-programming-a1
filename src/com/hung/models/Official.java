package com.hung.models;

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

    public Official(Integer id, String name, String state, int age) {
        super(id, name, state, age);
    }

    /**
     * Sorting input list of athletes according to their achieved time, then display result.
     * @param game Input list of athletes
     */
    public void summarise(Game game) {
        List<Athlete> athletes = game.getAthletes();

        // Sorting the list of athletes according to their achieved time
        Collections.sort(game.getAthletes(),  new Comparator<Athlete>() {
            @Override
            public int compare(Athlete o1, Athlete o2) {
                if (o1.getPreviousAchieveTime() > o2.getPreviousAchieveTime()) {
                    return 1;
                } else if (o1.getPreviousAchieveTime() < o2.getPreviousAchieveTime()) {
                    return -1;
                }
                return 0;
            }
        });

        athletes.get(0).setPreviousReceivedPoint(FIRST_PLACE_REWARD);
        athletes.get(1).setPreviousReceivedPoint(SECOND_PLACE_REWARD);
        athletes.get(2).setPreviousReceivedPoint(THIRD_PLACE_REWARD);

        // display result, also save the result as last result of the game
        StringBuilder resultSb = new StringBuilder(game.getId());
        resultSb.append("'s result:").append("\n");

        System.out.println("Result:");

        for (int i = 0; i < athletes.size(); i++) {
            Athlete a = athletes.get(i);
            StringBuilder sb = new StringBuilder("#");
            sb.append(i + 1).append(" ").append(a.getName()).append(" - ID: ")
                    .append(a.getId()).append(" - Time: ").append(a.getPreviousAchieveTime());

//            System.out.print("#" + (i + 1) + " ");
//            System.out.println(a.getName() + " - ID: " + a.getId() + " - Time: " + a.getPreviousAchieveTime());

            String result = sb.toString();
            resultSb.append(result).append("\n");
            System.out.println(result);
        }

        game.setLastResult(resultSb.toString());

        System.out.println();
    }
}
