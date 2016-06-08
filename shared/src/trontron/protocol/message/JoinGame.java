package trontron.protocol.message;

import java.io.Serializable;

/**
 * Message sent on new player connection
 */
public class JoinGame implements Serializable {

    /**
     * The name of the player
     */
    private String name;

    public JoinGame(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
