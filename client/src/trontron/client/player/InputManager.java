package trontron.client.player;

import trontron.client.thread.ServerHandler;
import trontron.protocol.message.ChangeDirection;
import trontron.model.world.Direction;

/**
 * Handles the user input
 */
public class InputManager {
    /**
     * The handled player
     */
    private Player player;

    /**
     * Constructor
     * @param player The player handle
     */
    public InputManager(Player player) {
        this.player = player;
    }

    /**
     * Handle an input
     * @param c The pressed key
     * @param serverHandler The server handler to notify
     */
    public void onInput(char c, ServerHandler serverHandler) {
        // determine the players new direction
        if (player.isItLeftKey(c)) {
            player.setActorDirection(Direction.left);
        }
        if (player.isItRightKey(c)) {
            player.setActorDirection(Direction.right);
        }
        if (player.isItUpKey(c)) {
            player.setActorDirection(Direction.up);
        }
        if (player.isItDownKey(c)) {
            player.setActorDirection(Direction.down);
        }

        // send the new direction to the server
        serverHandler.send(new ChangeDirection(player.getActorDirection(), player.getId()));
    }
}
