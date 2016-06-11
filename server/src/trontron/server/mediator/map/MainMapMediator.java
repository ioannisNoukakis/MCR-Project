package trontron.server.mediator.map;

import trontron.server.actor.manager.NonPlayableManager;
import trontron.server.actor.manager.PlayableManager;
import trontron.server.behaviour.Behaviour;
import trontron.server.mediator.map.LobbyMediator;
import trontron.server.mediator.map.MapMediator;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public class MainMapMediator extends MapMediator {

    private LobbyMediator lobby;

    public MainMapMediator(String mapName, int maxX, int maxY, LinkedList<Behaviour> listComp, int frequecySpawn, int maxSpawn, LobbyMediator lobby) {
        super(mapName, maxX, maxY, listComp, frequecySpawn, maxSpawn);
        this.lobby = lobby;
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
