package trontron.server.actor.manager;

import trontron.server.mediator.MediatorMap;
import trontron.server.player.Player;

/**
 * Created by durza9390 on 09.06.16.
 */
public abstract class PlayableManager extends ActorManager {

    private Player player;

    public PlayableManager(MediatorMap mediator) {
        super(mediator);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
