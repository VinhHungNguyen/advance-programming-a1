package com.hung;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class SuperAthlete extends Athlete {

    private Sport sport;

    @Override
    public int compete() {
        int range = 0;
        int min = 0;

        if (sport.getId().startsWith(Sport.ID_PREFIX_SWIMMING)) {
            range = Sport.RANGE_TIME_SWIMMING;
            min = Sport.MIN_TIME_SWIMMING;
        } else if (sport.getId().startsWith(Sport.ID_PREFIX_CYCLING)) {
            range = Sport.RANGE_TIME_CYCLING;
            min = Sport.MIN_TIME_CYCLING;
        } else if (sport.getId().startsWith(Sport.ID_PREFIX_RUNNING)) {
            range = Sport.RANGE_TIME_RUNNING;
            min = Sport.MIN_TIME_RUNNING;
        }

        return new Random(System.currentTimeMillis())
                .nextInt(range) + min;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
