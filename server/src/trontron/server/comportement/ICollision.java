package trontron.server.comportement;

import trontron.server.actor.manager.ActorManager;

/**
 * Created by durza9390 on 09.06.16.
 */
public interface ICollision {
    void solveCollision(ActorManager a, ActorManager b);
}
