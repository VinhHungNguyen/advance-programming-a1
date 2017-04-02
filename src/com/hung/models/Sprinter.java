package com.hung.models;

import com.hung.models.Athlete;
import com.hung.models.Game;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Sprinter extends Athlete {

    public Sprinter(Integer id, String name, String state, int age) {
        super(id, name, state, age);
    }

    @Override
    public int compete() {
        return new Random(System.currentTimeMillis())
                .nextInt(Game.RANGE_TIME_RUNNING) + Game.MIN_TIME_RUNNING;
    }

    @Override
    public String getPlayableGameIdPrefix() {
        return Game.ID_PREFIX_RUNNING;
    }
}
