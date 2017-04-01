package com.hung;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Cyclist extends Athlete {

    @Override
    public int compete() {
        return new Random(System.currentTimeMillis())
                .nextInt(Game.RANGE_TIME_CYCLING) + Game.MIN_TIME_CYCLING;
    }

    @Override
    public String getPlayableGameIdPrefix() {
        return Game.ID_PREFIX_CYCLING;
    }
}
