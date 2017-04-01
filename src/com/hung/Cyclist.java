package com.hung;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Cyclist extends Athlete {

    @Override
    public int compete() {
        return new Random(System.currentTimeMillis())
                .nextInt(Sport.RANGE_TIME_CYCLING) + Sport.MIN_TIME_CYCLING;
    }
}
