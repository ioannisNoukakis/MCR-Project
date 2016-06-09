package trontron.server.mediator;

import trontron.server.actor.manager.MotoManager;
import trontron.server.actor.manager.NonPlayableManager;
import trontron.server.actor.manager.PlayableManager;
import trontron.server.actor.manager.WorldManager;

/**
 * @author durza9390
 */
public class MediatorMapNormale extends MediatorMap {

    private MediatorLobby lobby;

    public MediatorMapNormale(String mapName, int maxX, int maxY, MediatorLobby lobby) {
        super(mapName, maxX, maxY);
        this.lobby = lobby;
    }

    @Override
    public void verifyMove(PlayableManager a) {
        
        for (PlayableManager b : listPlayableManager) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                //super.ChangeMotoMap(a, lobby);
            }
        }
        
        for(NonPlayableManager b : listNonPlayableManager)
        {
            if(checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                //super.ChangeMotoMap(a, lobby);
            }
        }
    }
}
