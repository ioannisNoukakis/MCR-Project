/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.Teleporter;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.Behaviour;
import trontron.server.mediator.MediatorMap;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public class TeleporterManager extends NonPlayableManager {

    private Teleporter teleporter;
    private MediatorMap mediatorDeDestination;

    public TeleporterManager(MediatorMap mediator, LinkedList<Behaviour> listComportement, Teleporter teleporter, MediatorMap mediatorDeDestination) {
        super(mediator, listComportement);
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
