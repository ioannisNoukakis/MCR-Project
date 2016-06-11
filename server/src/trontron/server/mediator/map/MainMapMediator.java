package trontron.server.mediator.map;

import trontron.server.actor.manager.NonPlayableManager;
import trontron.server.actor.manager.PlayableManager;

/**
 * @author durza9390
 */
public class MainMapMediator extends MapMediator {

    //private LobbyMediator lobby;

    public MainMapMediator(String mapName, int maxX, int maxY, int frequecySpawn, int maxSpawn/*, LobbyMediator lobby*/) {
        super(mapName, maxX, maxY, frequecySpawn, maxSpawn);
        //this.lobby = lobby;
    }

    @Override
    public void verifyMove(PlayableManager a) {
        
        for (PlayableManager b : getPlayableManagers()) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                a.handleCollision(b);
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
