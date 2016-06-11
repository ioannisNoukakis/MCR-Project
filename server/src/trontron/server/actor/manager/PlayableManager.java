package trontron.server.actor.manager;

import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;
import trontron.server.player.Player;

import java.util.List;

/**
 * Manager for a playable actor
 */
public abstract class PlayableManager extends ActorManager {
    /**
     * The player
     */
    private Player player;

    /**
     * Constructor
     * @param mediator
     * @param listComportement
     */
    public PlayableManager(MapMediator mediator, List<MapBehaviour> listComportement) {
        super(mediator, listComportement);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
