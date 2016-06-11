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
     * @param mediator The map mediator
     * @param behaviours The list of behaviours for the manager
     */
    public NonPlayableManager(MapMediator mediator, List<MapBehaviour> behaviours) {
        super(mediator, behaviours);
    }
}
