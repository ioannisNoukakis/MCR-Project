package trontron.server.actor.manager;

import trontron.server.behaviour.Behaviour;
import trontron.server.mediator.MediatorMap;
import trontron.server.player.Player;

import java.util.LinkedList;

/**
 * Created by durza9390 on 09.06.16.
 */
public abstract class PlayableManager extends ActorManager {

    private Player player;

    public PlayableManager(MediatorMap mediator, LinkedList<Behaviour> listComportement) {
        super(mediator, listComportement);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
