package com.hung.models;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by hungnguyen on 4/2/17.
 */
public class Official extends Participant {

    public Official(Integer id, String name, String state, int age) {
        super(id, name, state, age);
    }

    /**
     * Sorting input list of athletes according to their achieved time, then display result.
     * @param athletes Input list of athletes
     */
    public void summarise(List<Athlete> athletes) {
        // Sorting input list of athletes according to their achieved time
        Collections.sort(athletes,  new Comparator<Athlete>() {
            @Override
            public int compare(Athlete o1, Athlete o2) {
                if (o1.getPreviousAchieveTime() < o2.getPreviousAchieveTime()) {
                    return 1;
                } else if (o1.getPreviousAchieveTime() > o2.getPreviousAchieveTime()) {
                    return -1;
                }
                return 0;
            }
        });

        // display result
        System.out.println("Result:");
        for (int i = 0; i < athletes.size(); i++) {
            Athlete a = athletes.get(i);
            System.out.print("#" + (i + 1) + " ");
            System.out.println(a.getName() + " - ID: " + a.getId() + " - Time: " + a.getPreviousAchieveTime());
        }

        System.out.println();
    }
}
