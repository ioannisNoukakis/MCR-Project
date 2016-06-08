package trontron.server.mediator;

import trontron.server.actor.manager.MotoManager;
import trontron.server.actor.manager.TeleporterManager;
import trontron.server.actor.manager.WorldManager;
import trontron.model.world.Point2D;

/**
 * @author durza9390
 */
public class MediatorLobby extends MediatorMap {
    
    public MediatorLobby(String mapName, int maxX, int maxY) {
        super(mapName, maxX, maxY);
    }
    
    @Override
    public void verifyMove(MotoManager a) {
        for (TeleporterManager b : listeTeleporterManager) {
            if (checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                //System.out.println(b.getMediatorDeDestination().getClass().getName());
                super.ChangeMotoMap(a, b.getMediatorDeDestination());
            }
        }
        
        for(WorldManager b : listeWorld)
        {
            if (checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                a.getActor().setLocation(new Point2D(getMaxX()/2, getMaxY()/2));
                a.reset();
            }
        }
    }
}
