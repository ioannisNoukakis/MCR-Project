package mediator;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import ActorManager.TeleporterManager;

/**
 * @author durza9390
 */
public class MediatorLobby extends MediatorMap {

    public MediatorLobby(String mapName) {
        super(mapName);
    }
    
    @Override
    public void verifyMove(ActorManager a) {
        for (ActorManager b : super.getListActorManager()) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                if(a.getClass() == MotoManager.class && b.getClass() == TeleporterManager.class)
                {
                    super.ChangeActorMap(a, ((TeleporterManager)b).getMediatorDeDestination());
                }
            }
        }
    }
}
