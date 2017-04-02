package com.hung;

import java.util.HashSet;
import java.util.Set;

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

    private Set<Athlete> athletes;
    private Official official;

    private String id;

    public Game(String id) {
        this.id = id;
        athletes = new HashSet<>();
    }

    public boolean run() {
        // The number of participating athletes is not enough
        if (!isReadyToPlay()) {
            System.out.println("The game requires at least " + MIN_PARTICIPANTS + " athletes to run.");
            return false;
        }

        for (Athlete a : athletes) {
            a.compete();
        }

        return true;
    }

    /**
     * Add new athlete to this game.
     * If the game has reached maximum number of athletes or the athlete cannot join the game, print an alert then
     * return false. Otherwise, add the athlete then return true
     * @param athlete The athlete joining this game
     * @return True if adding successfully
     */
    public boolean addAthlete(Athlete athlete) {
        // Already reached max number of participants
        if (athletes.size() >= MAX_PARTICIPANTS) {
            System.out.println("This game has reached maximum number of participants (8 participants).");
            return false;
        }

        // The athlete cannot play this game
        if (!athlete.getPlayableGameIdPrefix().isEmpty() && !id.startsWith(athlete.getPlayableGameIdPrefix())) {
            System.out.println("This athlete cannot participate this game.");
            return false;
        }

        athletes.add(athlete);
        return true;
    }

    /**
     * Add all athletes from a given list of athletes
     * @param athletes The list or array of athletes
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

    public boolean isReadyToPlay() {
        return athletes.size() >= MIN_PARTICIPANTS;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(id);
        for (Athlete a : athletes) {
            sb.append("\n");
            sb.append(a.toString());
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }
}
