package com.hung;

/**
 * Created by hungnguyen on 4/1/17.
 */
public abstract class Athlete extends Participant {

    public Athlete(Integer id, String name, String state, int age) {
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
}
