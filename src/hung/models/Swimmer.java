package hung.models;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class Swimmer extends Athlete {

    public Swimmer(String id, String name, String state, int age) {
        super(id, name, state, age);
    }

    @Override
    public int compete() {
        return new Random().nextInt(Game.RANGE_TIME_SWIMMING) + Game.MIN_TIME_SWIMMING;
    }

    @Override
    public String getPlayableGameIdPrefix() {
        return Game.ID_PREFIX_SWIMMING;
    }
}
