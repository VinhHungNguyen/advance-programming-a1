package com.hung;

import java.util.ArrayList;
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

    public static final int MAX_PARTICIPANTS = 8;

    public static final String ID_PREFIX_SWIMMING = "S";
    public static final String ID_PREFIX_CYCLING = "C";
    public static final String ID_PREFIX_RUNNING = "R";

    private List<Athlete> athletes;

    private String id;

    public Game(String id) {
        this.id = id;
        athletes = new ArrayList<>();
    }

    /**
     * Add new athlete to this game.
     * If the game has reached maximum number of athletes or the athlete cannot join the game, print an alert then
     * return false. Otherwise, add the athlete then return true
     * @param athlete The athlete joining this game
     * @return True if adding successfully
     */
    public boolean addAthlete(Athlete athlete) {
        if (athletes.size() < MAX_PARTICIPANTS) {
            System.out.println("This game has reached maximum number of participants (8 participants).");
            return false;
        }

        if (!athlete.getPlayableGameIdPrefix().isEmpty() && !id.startsWith(athlete.getPlayableGameIdPrefix())) {
            System.out.println("This athlete cannot participate this game.");
            return false;
        }

        athletes.add(athlete);
        return true;
    }

    public String getId() {
        return id;
    }
}
