package trontron.model.actor;

import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 * The base class for an actor
 */
public abstract class Actor implements Serializable {
    /**
     * The id of the actor
     */
    private final int id;

    /**
     * The name of the actor
     */
    private final String name;

    /**
     * The actors current location
     */
    private Point2D location;

    /**
     * The actors current speed
     */
    private float speed;

    /**
     * The actors base speed
     */
    private final float BaseSpeed;

    /**
     * The actors current direction
     */
    private Direction direction;

    /**
     * The actors current height
     */
    private int height;

    /**
     * The actors current width
     */
    private int width;

    /**
     * The number of kills of this actor.
     */
    private int kills;

    /**
     * Constructor
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param width The width
     * @param height The height
     */
    public Actor(int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.speed = speed;
        this.direction = direction;
        this.height = height;
        this.width = width;
        BaseSpeed = speed;
        kills = 0;
    }

    /**
     * Gets the id of the actor
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the actor
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the height of the actor
     * @param height The new value
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the width of the actor
     * @param width The new value
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the location of the actor
     * @param location The new value
     */
    public void setLocation(Point2D location) {
        this.location = location;
    }

    /**
     * Gets the current location of the actor
     * @return The location
     */
    public Point2D getLocation() {
        return location;
    }

    /**
     * Gets the current speed of the actor
     * @return The speed
     */
    public float getSpeed() {
        return speed;
    }

    /**
     * Sets the speed of the actor
     * @param speed The new value
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Gets the current direction of the actor
     * @return The direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the direction of the actor
     * @param direction The new value
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Gets the current height of the actor
     * @return The height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Gets the current width of the actor
     * @return The width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the base speed of the actor
     * @return The speed
     */
    public float getBaseSpeed() {
        return BaseSpeed;
    }

    /**
     * Gets the number of kills of the actor
     * @return The speed
     */
    public int getKills() {
        return kills;
    }

    /**
     * Set the number of kills of the actor
     * @param kills: the number of kills
     */
    public void setKills(int kills) {
        this.kills = kills;
    }
}
