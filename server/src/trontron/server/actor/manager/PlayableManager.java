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
     * The Player of this PlayableManager
     */
    private Player player;

    /**
     * Constructor
     * @param mediator: the mediator in where this actor will be.
     * @param listBehaviour: list of Behaviour of this actor.
     */
    public PlayableManager(MapMediator mediator, List<MapBehaviour> listBehaviour) {
        super(mediator, listBehaviour);
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
