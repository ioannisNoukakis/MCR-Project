package trontron.server.mediator.map;

import trontron.server.actor.manager.NonPlayableManager;
import trontron.server.actor.manager.PlayableManager;
import trontron.server.behaviour.Behaviour;

import java.util.LinkedList;

/**
 * Created by durza9390 on 10.06.16.
 */
public class MediatorMapInverted extends MapMediator {

    public MediatorMapInverted(String mapName, int maxX, int maxY, LinkedList<Behaviour> listComp, int frequecySpawn, int maxSpawn, LobbyMediator lobby) {
        super(mapName, maxX, maxY, listComp, frequecySpawn, maxSpawn);
    }

    @Override
    public void verifyMove(PlayableManager a) {

        for (PlayableManager b : getPlayableManagers()) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                b.handleCollision(a);
            }
        }

        for(NonPlayableManager b : getNonPlayableManagers())
        {
            if(checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                b.handleCollision(a);
            }
        }
    }
}
