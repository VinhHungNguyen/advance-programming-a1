package com.hung.models;

import com.hung.models.Athlete;
import com.hung.models.Game;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class SuperAthlete extends Athlete {

    public SuperAthlete(Integer id, String name, String state, int age) {
        super(id, name, state, age);
    }

    @Override
    public int compete() {
        int range = 0;
        int min = 0;

        if (game.getId().startsWith(Game.ID_PREFIX_SWIMMING)) {
            range = Game.RANGE_TIME_SWIMMING;
            min = Game.MIN_TIME_SWIMMING;
        } else if (game.getId().startsWith(Game.ID_PREFIX_CYCLING)) {
            range = Game.RANGE_TIME_CYCLING;
            min = Game.MIN_TIME_CYCLING;
        } else if (game.getId().startsWith(Game.ID_PREFIX_RUNNING)) {
            range = Game.RANGE_TIME_RUNNING;
            min = Game.MIN_TIME_RUNNING;
        }

        return new Random(System.currentTimeMillis())
                .nextInt(range) + min;
    }

    @Override
    public String getPlayableGameIdPrefix() {
        return "";
    }

}
