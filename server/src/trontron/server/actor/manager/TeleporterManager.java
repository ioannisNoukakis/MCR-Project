/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.Teleporter;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Manager for a Teleporter.
 */
public class TeleporterManager extends NonPlayableManager {

    /**
     * The Teleporter of this TeleporterManager
     */
    private Teleporter teleporter;

    /**
     * The desitnation MapMediator of this teleporter.
     */
    private MapMediator mediatorDeDestination;

    /**
     * Constructor
     * @param mediator: the mediator in where this actor will be.
     * @param listBehaviour: list of Behaviour of this actor.
     * @param teleporter: the teleporter
     * @param mediatorDeDestination: the MapMediator destination of this teleporter
     */
    public TeleporterManager(MapMediator mediator, List<MapBehaviour> listBehaviour, Teleporter teleporter, MapMediator mediatorDeDestination) {
        super(mediator, listBehaviour);
        this.teleporter = teleporter;
        this.mediatorDeDestination = mediatorDeDestination;
    }

    /**
     * Returns this manager's actor.
     *
     * @return Actor
     */
    @Override
    public Actor getActor() {
        return teleporter;
    }

    /**
     * Returns this manager's lethal hitbox. That means when this hitbox hits an killing hitbox
     * it applies an effect on this manager's moto.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getlethalHitbox() {
        return new Rectangle2D[1];
    }

    /**
     * Returns this teleporter activate hitbox.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getKillingHitbox()  {
        Rectangle2D[] rect = new Rectangle2D[1];
        rect[0] = new Rectangle2D(teleporter.getLocation(), teleporter.getWidth(), teleporter.getHeight());
        return rect; 
    }

    /**
     * Returns the MapMediator where this teleporter teleports.
     *
     * @return MapMediator
     */
    public MapMediator getMediatorDeDestination() {
        return mediatorDeDestination;
    }

    /**
     * Compute this actor's state after a given time.
     *
     * @param delta: the time elapsed between two updates
     */
    @Override
    public void onUpdate(int delta) {
        //y'a rien à mettre à jour c'est un téléporteur
    }

    /**
     * Reset this actor.
     */
    @Override
    public void reset() {
    }
}
