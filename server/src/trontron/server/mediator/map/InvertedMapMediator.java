package trontron.server.mediator.map;

import trontron.server.actor.manager.NonPlayableManager;
import trontron.server.actor.manager.PlayableManager;

/**
 * Created by durza9390 on 10.06.16.
 */
public class InvertedMapMediator extends MapMediator {

    public InvertedMapMediator(String mapName, int maxX, int maxY, int frequecySpawn, int maxSpawn) {
        super(mapName, maxX, maxY, frequecySpawn, maxSpawn);
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
