package trontron.server.mediator;

import trontron.server.actor.manager.*;
import trontron.model.world.Point2D;

/**
 * @author durza9390
 */
public class MediatorLobby extends MediatorMap {
    
    public MediatorLobby(String mapName, int maxX, int maxY) {
        super(mapName, maxX, maxY);
    }
    
    @Override
    public void verifyMove(PlayableManager a) {
        for (NonPlayableManager b : listNonPlayableManager) {
            if (checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                //System.out.println(b.getMediatorDeDestination().getClass().getName());
                //super.ChangeMotoMap(a, b.getMediatorDeDestination());
                //a.getActor().setLocation(new Point2D(getMaxX()/2, getMaxY()/2));
                //a.reset();
            }
        }
    }
}
