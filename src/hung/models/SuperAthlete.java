package hung.models;

import java.util.Random;

/**
 * Created by hungnguyen on 4/1/17.
 */
public class SuperAthlete extends Athlete {

    public SuperAthlete(String id, String name, String state, int age) {
        super(id, name, state, age);
        type = Participant.TYPE_SUPER;
    }

    @Override
    public int compete() {
        int range = 0;
        int min = 0;

//        System.out.println(this);
//        System.out.println(game.getId());

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

        return new Random().nextInt(range) + min;
    }

    @Override
    public String getPlayableGameIdPrefix() {
        return "";
    }

}
