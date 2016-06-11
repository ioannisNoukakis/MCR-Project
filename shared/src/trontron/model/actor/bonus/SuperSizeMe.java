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
     * The concerned actor
     */
    private Actor actor;

    /**
     * The actors original width
     */
    private int originalWidth;

    /**
     * The actors original height
     */
    private int originalHeight;

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
            this.actor = actor;
            originalWidth = actor.getWidth();
            originalHeight = actor.getHeight();
            actor.setWidth(actor.getWidth() * 2);
            actor.setHeight(actor.getHeight() * 2);
        }
    }

    /**
     * Deactivates the bonus
     */
    @Override
    public void deactivate() {
        super.deactivate();
        actor.setWidth(originalWidth);
        actor.setHeight(originalHeight);
    }
}
