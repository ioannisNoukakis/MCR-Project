package trontron.server.actor.manager;

import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;
import trontron.server.player.Player;

import java.util.List;

/**
 * Manager for a playable actor
 */
public abstract class PlayableManager extends ActorManager {

    private Player player;

    public PlayableManager(MapMediator mediator, List<MapBehaviour> listComportement) {
        super(mediator, listComportement);
    }

    /**
     * Set a player for this PlayableManager.
     *
     * @param player: the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Returns the player of this PlayableManager.
     */
    public Player getPlayer() {
        return player;
    }

}
