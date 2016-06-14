package trontron.protocol.message;

import java.io.Serializable;

/**
 * Message sent to communicate a players id
 */
public class PlayerIdentity implements Serializable {
    /**
     * The players id
     */
    private int id;

    /**
     * Constructor
     * @param id The id of the player
     */
    public PlayerIdentity(int id) {
        this.id = id;
    }

    /**
     * Gets the id of the player
     * @return
     */
    public int getId() {
        return id;
    }
}
