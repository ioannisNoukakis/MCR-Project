package trontron.server.behaviour;

import trontron.server.actor.manager.ActorManager;
import trontron.server.mediator.map.MapMediator;

/**
 * Behaviour of an actor on a given map
 */
public class MapBehaviour implements ICollisionBehaviour {
    /**
     * The behaviour in case of a collision
     */
    private ICollisionBehaviour behaviour;

    /**
     * The map concerned by this behaviour
     */
    private MapMediator map;

    /**
     * Constructor
     * @param map The map concerned by the behaviour
     * @param behaviour The behaviour
     */
    public MapBehaviour(MapMediator map, ICollisionBehaviour behaviour) {
        this.map = map;
        this.behaviour = behaviour;
    }

    /**
     * Handles a collision between two actors
     * @param a The first actor
     * @param b The second actor
     */
    @Override
    public void solveCollision(ActorManager a, ActorManager b) {
        behaviour.solveCollision(a, b);
    }

    /**
     * Gets the map for this behaviour
     * @return The map mediator
     */
    public MapMediator getMap() {
        return map;
    }
}
