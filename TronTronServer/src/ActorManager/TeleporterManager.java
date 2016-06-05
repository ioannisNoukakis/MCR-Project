/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActorManager;

import Models.Actor.Actor;
import Models.Actor.Teleporter;
import Models.world.Rectangle2D;
import mediator.MediatorMap;

/**
 * @author durza9390
 */
public class TeleporterManager extends ActorManager {

    private Teleporter teleporter;
    private MediatorMap mediatorDeDestination;

    public TeleporterManager(Teleporter teleporter, MediatorMap mediatorDeDestination, MediatorMap mediator) {
        super(mediator);
        this.teleporter = teleporter;
        this.mediatorDeDestination = mediatorDeDestination;
    }

    @Override
    public Actor getActor() {
        return teleporter;
    }

    @Override
    public Rectangle2D[] getlethalHitbox() {
        return new Rectangle2D[1];
    }

    @Override
    public Rectangle2D[] getKillingHitbox()  {
        Rectangle2D[] rect = new Rectangle2D[1];
        rect[0] = new Rectangle2D(teleporter.getLocation(), teleporter.getWidth(), teleporter.getHeight());
        return rect; 
    }

    public MediatorMap getMediatorDeDestination() {
        return mediatorDeDestination;
    }

    @Override
    public void onUpdate(int delta) {
        //y'a rien à mettre à jour c'est un téléporteur
    }

    @Override
    public void reset() {
        
    }
}
