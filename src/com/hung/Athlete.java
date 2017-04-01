package com.hung;

/**
 * Created by hungnguyen on 4/1/17.
 */
public abstract class Athlete extends Participant {

    /**
     * Randomly generate a time which the athlete finishes a game.
     * The range of random time depends on the sport this athlete plays.
     */
    public abstract int compete();
}
