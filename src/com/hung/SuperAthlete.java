package com.hung;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class SuperAthlete extends Athlete {

    private Game game;

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
