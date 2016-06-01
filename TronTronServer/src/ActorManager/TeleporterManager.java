/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActorManager;

import Models.Actor.Actor;
import Models.world.Rectangle2D;
import mediator.Mediator;

/**
 * @author durza9390
 */
public class TeleporterManager extends ActorManager {

    private Rectangle2D warpingZone;

    public TeleporterManager(Mediator mediator, Rectangle2D warpingZone) {
        super(mediator);
        this.warpingZone = warpingZone;
    }

    @Override
    public Actor getActor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D[] getKillingHitbox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ActorManager getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Rectangle2D[] getlethalHitbox() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onUpdate(int delta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
