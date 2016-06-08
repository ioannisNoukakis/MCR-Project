package trontron.protocol.message;

import trontron.model.world.Direction;
import java.io.Serializable;

/**
 * Message sent when a player changes its direction
 */
public class ChangeDirection implements Serializable{
    /**
     * The new directon
     */
    private Direction newDirection;

    /**
     * The players id
     */
    private int playerId;

    public ChangeDirection(Direction newDirection, int playerId) {
        this.newDirection = newDirection;
        this.playerId = playerId;
    }
    
    public Direction getNewDirection() {
        return newDirection;
    }

    public int getPlayerId() {
        return playerId;
    }
 
}
