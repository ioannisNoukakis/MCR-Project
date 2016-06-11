package trontron.model.actor;

import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 * A playable actor
 */
public abstract class Playable extends Actor implements Serializable {
    /**
     * Indicates if the actor is alive or not
     */
    private boolean isAlive;

    /**
     * Constructor
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param width The width
     * @param height The height
     */
    public Playable(int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(id, name, location, speed, direction, height, width);
        isAlive = true;
    }

    /**
     * Indicates if the actor is alive
     * @return True if alive, false otherwise
     */
    public boolean isAlive() {
        return isAlive;
    }

//    public void kill()
//    {
//        super.setSpeed(0);
//        isAlive = false;
//    }

//    public void hallelujah()
//    {
//        super.setSpeed(super.getBaseSpeed());
//        isAlive = true;
//    }
}
