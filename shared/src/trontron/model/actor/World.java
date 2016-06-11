package trontron.model.actor;

import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 * Represents a world
 */
public class World extends Actor implements Serializable {
    /**
     * Constructor
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param direction The initial direction
     * @param width The width
     * @param height The height
     */
    public World(int id, String name, Point2D location, float speed, Direction direction, int width, int height) {
        super(id, name, location, speed, direction, height, width);
    }
}
