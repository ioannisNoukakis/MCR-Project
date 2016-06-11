package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Manages an actor.
 */
public abstract class ActorManager {
    /**
     * The mediator where this actor is currently.
     */
    private MapMediator mediator;

    /**
     * The list of behaviour that this actor has for each map.
     */
    private List<MapBehaviour> listBehaviour;

    /**
     * Constructor
     * @param mediator: the mediator in where this actor will be.
     * @param listBehaviour: list of Behaviour of this actor.
     */
    public ActorManager(MapMediator mediator, List<MapBehaviour> listBehaviour) {
        this.mediator = mediator;
        this.listBehaviour = listBehaviour;
    }

    /**
     * Compute an actor's state after a given time.
     *
     * @param delta: the time elapsed between two updates
     */
    public abstract void onUpdate(int delta);

    /**
     * Returns this manager's actor.
     *
     * @return Actor
     */
    public abstract Actor getActor();

    /**
     * Returns this manager's killing hitbox. That means when an other actor hits this
     * hitbox it applies an effect on him.
     *
     * @return Rectangle2D[]
     */
    public abstract Rectangle2D[] getKillingHitbox();

    /**
     * Returns this manager's lethal hitbox. That means when this hitbox hits an killing hitbox
     * it applies an effect on this manager's moto.
     *
     * @return Rectangle2D[]
     */
    public abstract Rectangle2D[] getlethalHitbox();

    /**
     * Reset this actor.
     */
    public abstract void reset();

    /**
     * Returns the MapMediator where this actor is currently in.
     *
     * @return MapMediator
     */
    public MapMediator getMediator() {
        return mediator;
    }

    /**
     * Changes this actor location to an other map.
     *
     * @param mediator: the destination map.
     */
    public void setMediator(MapMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Handle the collision between this actor and a playable.
     *
     * @param b: the playable.
     */
    public void handleCollision(PlayableManager b) {
        for(MapBehaviour behaviour : listBehaviour) {
            if(behaviour.getMap().equals(mediator)) {
                behaviour.solveCollision(b, this);
            }
        }
    }
}
