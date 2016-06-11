package trontron.server.behaviour;

import trontron.server.actor.manager.ActorManager;

/**
 * Created by durza9390 on 09.06.16.
 */
public class Behaviour implements ICollisionBehaviour {

    private ICollisionBehaviour behaviour;
    private String mapName;

    public Behaviour(ICollisionBehaviour behaviour, String mapName) {
        this.behaviour = behaviour;
        this.mapName = mapName;
    }

    @Override
    public void solveCollision(ActorManager a, ActorManager b) {
        behaviour.solveCollision(a, b);
    }

    public String getMapName() {
        return mapName;
    }
}
