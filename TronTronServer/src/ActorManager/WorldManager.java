/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ActorManager;

import Models.Actor.Actor;
import Models.Actor.World;
import Models.world.Point2D;
import Models.world.Rectangle2D;
import mediator.MediatorMap;

/**
 *
 * @author durza9390
 */
public class WorldManager extends ActorManager{
    private World world;

    public WorldManager(World world, MediatorMap mediator) {
        super(mediator);
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
