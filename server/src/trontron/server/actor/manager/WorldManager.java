/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.World;
import trontron.model.world.Point2D;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Manages a map.
 */
public class WorldManager extends NonPlayableManager {
    private World world;

    public WorldManager(MapMediator mediator, List<MapBehaviour> listComportement, World world) {
        super(mediator, listComportement);
        this.world = world;
    }

    /**
     * Returns this manager's actor.
     *
     * @return Actor
     */
    @Override
    public Actor getActor() {
        return world;
    }

    /**
     * Returns this manager's killing hitbox. That means when an other actor hits this
     * hitbox it applies an effect on him.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getKillingHitbox() {
        Rectangle2D[] hit = new Rectangle2D[4];
        hit[0] = new Rectangle2D(new Point2D(0, 0), world.getWidth(), 0);
        hit[1] = new Rectangle2D(new Point2D(0, 0), 0, world.getHeight());
        hit[2] = new Rectangle2D(new Point2D(0, world.getHeight()), world.getWidth(), 0);
        hit[3] = new Rectangle2D(new Point2D(world.getWidth(), 0), 0, world.getHeight());
        return hit;
    }

    /**
     * Returns this manager's lethal hitbox. That means when this hitbox hits an killing hitbox
     * it applies an effect on this manager's moto.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getlethalHitbox() {
        return new Rectangle2D[0];
    }

    /**
     * Compute this actor's state after a given time.
     *
     * @param delta: the time elapsed between two updates
     */
    @Override
    public void onUpdate(int delta) {
    }

    /**
     * Reset this actor.
     */
    @Override
    public void reset() {
    }
}
