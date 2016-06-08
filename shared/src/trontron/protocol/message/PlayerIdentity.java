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

    public PlayerIdentity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
