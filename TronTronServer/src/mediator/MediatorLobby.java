package mediator;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import ActorManager.TeleporterManager;
import ActorManager.WorldManager;
import MAth.RandomUniformGenerator;
import Models.world.Point2D;

/**
 * @author durza9390
 */
public class MediatorLobby extends MediatorMap {
    
    public MediatorLobby(String mapName, int maxX, int maxY) {
        super(mapName, maxX, maxY);
    }
    
    @Override
    public void verifyMove(ActorManager a) {
        for (ActorManager b : super.getListActorManager()) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                if(a.getClass() == MotoManager.class && b.getClass() == TeleporterManager.class)
                {
                    super.ChangeActorMap(a, ((TeleporterManager)b).getMediatorDeDestination());
                }
                if(a.getClass() == MotoManager.class && b.getClass() == WorldManager.class)
                {
                    a.getActor().setLocation(new Point2D(getMaxX()/2, getMaxY()/2));
                    a.reset();
                }
            }
        }
    }
}
