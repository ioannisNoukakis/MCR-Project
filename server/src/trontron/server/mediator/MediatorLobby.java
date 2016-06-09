package trontron.server.mediator;

import trontron.server.actor.manager.*;
import trontron.model.world.Point2D;
import trontron.server.comportement.Comportement;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public class MediatorLobby extends MediatorMap {

    public MediatorLobby(String mapName, int maxX, int maxY, LinkedList<Comportement> listComp, int frequecySpawn, int maxSpawn) {
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
