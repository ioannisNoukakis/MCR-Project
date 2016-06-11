package trontron.server.actor.manager;

import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Manager for non playable actors
 */
public abstract class NonPlayableManager extends ActorManager {

    /**
     * Constructor
     * @param mediator: the mediator in where this actor will be.
     * @param listBehaviour: list of Behaviour of this actor.
     */
    public NonPlayableManager(MapMediator mediator, List<MapBehaviour> listBehaviour) {
        super(mediator, listBehaviour);
    }
}
