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
import trontron.server.behaviour.Behaviour;
import trontron.server.mediator.MediatorMap;

import java.util.LinkedList;

/**
 *
 * @author durza9390
 */
public class WorldManager extends NonPlayableManager{
    private World world;

    public WorldManager(MediatorMap mediator, LinkedList<Behaviour> listComportement, World world) {
        super(mediator, listComportement);
        this.world = world;
    }

    @Override
    public Actor getActor() {
        return world;
    }

    @Override
    public Rectangle2D[] getKillingHitbox() {
        Rectangle2D[] hit = new Rectangle2D[4];
        hit[0] = new Rectangle2D(new Point2D(0, 0), world.getWidth(), 0);
        hit[1] = new Rectangle2D(new Point2D(0, 0), 0, world.getHeight());
        hit[2] = new Rectangle2D(new Point2D(0, world.getHeight()), world.getWidth(), 0);
        hit[3] = new Rectangle2D(new Point2D(world.getWidth(), 0), 0, world.getHeight());
        return hit;
    }

    @Override
    public Rectangle2D[] getlethalHitbox() {
        return new Rectangle2D[0];
    }

    @Override
    public void onUpdate(int delta) {
    }

    @Override
    public void reset() {
    }
}
