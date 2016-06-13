package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 * Represents a bonus
 */
public abstract class Bonus extends Actor implements Serializable {
    /**
     * The lifetime of the bonus in milliseconds
     */
    private int timeToLive;

    /**
     * Indicates if the bonus is active
     */
    private boolean isActivated;

    /**
     * The concerned actor
     */
    private Actor actor;

    /**
     * Constructor
     * @param timeToLive The lifetime of the bonus in milliseconds
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param width The width
     * @param height The height
     */
    public Bonus(int timeToLive, int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(id, name, location, speed, direction, height, width);
        this.timeToLive = timeToLive;
        isActivated = false;
    }

    /**
     * Gets the actor for this bonus.
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Checks if the bonus is still alive and decrements its lifetime
     * @param delta The time to decrement in milliseconds
     * @return True if the bonus is still alive, false otherwise
     */
    public boolean isOver(int delta) {
        return (timeToLive -= delta) <= 0;
    }

    /**
     * Indicates if the bonus is activated
     * @return True if activated, false otherwise
     */
    public boolean isActivated() {
        return isActivated;
    }

    /**
     * Activates the bonus
     * @param actor The concerned actor
     */
    public void activate(Actor actor) {
        isActivated = true;
        this.actor = actor;
    }

    /**
     * Deactivates the bonus
     */
    public void deactivate() {
        isActivated = false;
    }
}
