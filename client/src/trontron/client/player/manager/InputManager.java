package trontron.client.player.manager;

import trontron.protocol.message.ChangeDirection;
import trontron.model.world.Direction;

import trontron.client.sync.SyncServer;

/**
 * @author durza9390
 */
public class InputManager {

    Player player;

    public InputManager(Player player) {
        this.player = player;
    }

    public void onInput(char c, SyncServer sync) throws Exception {
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
        sync.getOut().writeObject(new ChangeDirection(player.getActorDirection(), player.getId()));
        sync.getOut().flush();
    }

}
