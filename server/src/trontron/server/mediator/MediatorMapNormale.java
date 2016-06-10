package trontron.server.mediator;

import trontron.server.actor.manager.NonPlayableManager;
import trontron.server.actor.manager.PlayableManager;
import trontron.server.behaviour.Behaviour;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public class MediatorMapNormale extends MediatorMap {

    private MediatorLobby lobby;

    public MediatorMapNormale(String mapName, int maxX, int maxY, LinkedList<Behaviour> listComp, int frequecySpawn, int maxSpawn, MediatorLobby lobby) {
        super(mapName, maxX, maxY, listComp, frequecySpawn, maxSpawn);
        this.lobby = lobby;
    }

    @Override
    public void verifyMove(PlayableManager a) {
        
        for (PlayableManager b : getListPlayableManager()) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                a.handleCollision(b);
            }
        }
        
        for(NonPlayableManager b : getListNonPlayableManager())
        {
            if(checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                b.handleCollision(a);
            }
        }
    }
}
