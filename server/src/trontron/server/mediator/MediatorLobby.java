package trontron.server.mediator;

import trontron.server.actor.manager.*;
import trontron.server.behaviour.Behaviour;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public class MediatorLobby extends MediatorMap {

    public MediatorLobby(String mapName, int maxX, int maxY, LinkedList<Behaviour> listComp, int frequecySpawn, int maxSpawn) {
        super(mapName, maxX, maxY, listComp, frequecySpawn, maxSpawn);
    }

    @Override
    public void verifyMove(PlayableManager a) {
        for (NonPlayableManager b : getListNonPlayableManager()) {
            if (checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                b.handleCollision(a);
            }
        }
    }
}
