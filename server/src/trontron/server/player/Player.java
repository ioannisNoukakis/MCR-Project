package trontron.server.player;

import trontron.server.actor.manager.PlayableManager;
import trontron.server.thread.ClientHandler;

/**
 * A connected player
 */
public class Player {
    /**
     * The manager for the player
     */
    private PlayableManager manager;

    /**
     * The id of the player
     */
    private final int id;

    /**
     * The name of the player
     */
    private final String name;

    /**
     * The client handler used to communicate with the client
     */
    private ClientHandler handler;

    /**
     * Constructor
     * @param manager The manager for the player
     * @param id The id of the player
     * @param name The name of the player
     * @param handler The client handler used to communicate with the client
     */
    public Player(PlayableManager manager, int id, String name, ClientHandler handler) {
        this.manager = manager;
        this.id = id;
        this.name = name;
        this.handler = handler;
    }

    /**
     * Sends a message to the client
     * @param message The message object
     */
    public void send(Object message) {
        handler.send(message);
    }

    /**
     * Gets the manager of the player
     * @return The manager
     */
    public PlayableManager getManager() {
        return manager;
    }

    /**
     * Gets the id of the player
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the name of the player
     * @return The name
     */
    public String getName() {
        return name;
    }
}
