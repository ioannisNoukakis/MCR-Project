package trontron.protocol.message;

import trontron.model.world.Direction;
import java.io.Serializable;

/**
 * Message sent when a player changes its direction
 */
public class ChangeDirection implements Serializable{
    /**
     * The new direction
     */
    private Direction newDirection;

    /**
     * The players id
     */
    private int playerId;

    /**
     * Constructor
     * @param newDirection The new direction of the player
     * @param playerId The id of the player
     */
    public ChangeDirection(Direction newDirection, int playerId) {
        this.newDirection = newDirection;
        this.playerId = playerId;
    }

    /**
     * Gets the direction of the player
     * @return The direction
     */
    public Direction getNewDirection() {
        return newDirection;
    }

    /**
     * Gets the id of the player
     * @return The id
     */
    public int getPlayerId() {
        return playerId;
    }
}
