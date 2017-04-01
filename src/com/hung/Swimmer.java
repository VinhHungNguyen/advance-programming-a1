package com.hung;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Swimmer extends Athlete {

    @Override
    public int compete() {
        return new Random(System.currentTimeMillis())
                .nextInt(Game.RANGE_TIME_SWIMMING) + Game.MIN_TIME_SWIMMING;
    }

    @Override
    public String getPlayableGameIdPrefix() {
        return Game.ID_PREFIX_SWIMMING;
    }
}
