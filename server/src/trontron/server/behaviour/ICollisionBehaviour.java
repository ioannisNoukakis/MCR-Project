package trontron.server.behaviour;

import trontron.server.actor.manager.ActorManager;

/**
 * Interface for collision behaviour
 */
public interface ICollisionBehaviour {
    /**
     * Handle the collision between two actors
     * @param a The first actor
     * @param b The second actor
     */
    void solveCollision(ActorManager a, ActorManager b);
}
