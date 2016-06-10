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

    /**
     * Constructor
     * @param playerName The name of the player
     */
    public JoinGame(String playerName) {
        this.name = playerName;
    }

    /**
     * Gets the name of the player
     * @return The name
     */
    public String getName() {
        return name;
    }
}
