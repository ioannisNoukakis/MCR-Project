package mediator;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import ActorManager.TeleporterManager;
import ActorManager.WorldManager;
import Models.world.Point2D;

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

    public int getComportement(MotoManager moto) {
        return 2;
    }
}
