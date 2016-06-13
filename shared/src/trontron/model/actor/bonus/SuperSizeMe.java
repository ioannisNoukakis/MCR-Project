package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 * A bonus which changes the size of an actor
 */
public class SuperSizeMe extends Bonus implements Serializable {

    /**
     * Constructor
     * @param timeToLive The lifetime of the bonus
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param width The width
     * @param height The height
     */
    public SuperSizeMe(int timeToLive, int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(timeToLive, id, name, location, speed, direction, height, width);
    }

    /**
     * Activates the bonus
     * @param actor The concerned actor
     */
    @Override
    public void activate(Actor actor) {
        if(!isActivated()) {
            super.activate(actor);
            getActor().setWidth(getActor().getWidth() * 2);
            getActor().setHeight(getActor().getHeight() * 2);
        }
    }

    /**
     * Deactivates the bonus
     */
    @Override
    public void deactivate() {
        super.deactivate();
        getActor().setWidth(getActor().getOriginalWidth());
        getActor().setHeight(getActor().getOriginalHeight());
    }
}
