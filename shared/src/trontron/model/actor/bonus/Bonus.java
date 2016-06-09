package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public abstract class Bonus extends Actor implements Serializable {

    private int timeToLive;
    private boolean isActivated;

    public Bonus(int timeToLive, int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(id, name, location, speed, direction, height, width);
        this.timeToLive = timeToLive;
        isActivated = false;
    }

    public boolean isOver(int delta) {
        return (timeToLive -= delta) <= 0;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void activate(Actor a)
    {
        isActivated = true;
    }

    public abstract void desactivate();
}
