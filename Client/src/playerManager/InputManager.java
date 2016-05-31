/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerManager;

import Models.Protocol.Sync.ClientUpdate;
import Models.world.Direction;
import java.util.List;
import snycServer.SyncServer;

/**
 *
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
        sync.getOut().writeObject(new ClientUpdate(player.getActorDirection(), player.getId()));
        sync.getOut().flush();
    }

}
