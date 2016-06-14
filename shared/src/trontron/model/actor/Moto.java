package trontron.model.actor;

import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Represents a moto
 */
public class Moto extends Playable implements Serializable {
    /**
     * The tail of the moto
     */
    private LinkedList<Point2D> tail;

    /**
     * The maximal size of the tail
     */
    private float maxTailSize;

    /**
     * The current size of the tail
     */
    private float tailSize;

    /**
     * Constructor
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param width The width
     * @param height The height
     * @param tailSize The initial tail size
     */
    public Moto(int id, String name, Point2D location, float speed, int height, int width, int tailSize) {
        super(id, name, location, speed, Direction.none, height, width);
        this.maxTailSize = tailSize;
        tail = new LinkedList<>();
    }

    /**
     * Gets the points composing the tail
     * @return The list of points
     */
    public LinkedList<Point2D> getTail() {
        return tail;
    }

    /**
     * Gets the maximal size of the tail
     * @return The size
     */
    public float getMaxTailSize() {
        return maxTailSize;
    }

//    public float getTailSize() {
//        return tailSize;
//    }

//    public void setTailSize(int tailSize) {
//        this.tailSize = tailSize;
//    }

//    public void setMaxTailSize(int maxTailSize) {
//        this.maxTailSize = maxTailSize;
//    }
}
