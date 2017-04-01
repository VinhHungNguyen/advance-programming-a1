package com.hung;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Sprinter extends Athlete {

    @Override
    public int compete() {
        return new Random(System.currentTimeMillis())
                .nextInt(Game.RANGE_TIME_RUNNING) + Game.MIN_TIME_RUNNING;
    }
}
