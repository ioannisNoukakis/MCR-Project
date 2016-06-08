package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

/**
 *
 * @author durza9390
 */
public abstract class Bonus extends Actor {

    private int timeToLive;
    private Actor me;

    public Bonus(int timeToLive, Actor me, int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(id, name, location, speed, direction, height, width);
        this.timeToLive = timeToLive;
        this.me = me;
    }
    public boolean isOver(int delta) {
        return (timeToLive -= delta) <= 0;
    }

    public Actor getMe() {
        return me;
    }
}
