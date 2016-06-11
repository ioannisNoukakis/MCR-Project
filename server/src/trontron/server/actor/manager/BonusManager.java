package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.bonus.Bonus;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Manages a bonus.
 */
public class BonusManager extends NonPlayableManager {
    /**
     * The Bonus of this BonusManager
     */
    private Bonus bonus;

    /**
     * Constructor
     * @param mediator: the mediator in where this actor will be.
     * @param listBehaviour: list of Behaviour of this actor.
     * @param bonus: the bonus
     */
    public BonusManager(MapMediator mediator, List<MapBehaviour> listBehaviour, Bonus bonus) {
        super(mediator, listBehaviour);
        this.bonus = bonus;
    }

    /**
     * If the bonus was activated computes how much time it has before it removes itself.
     *
     * @param delta: the time elapsed between two updates
     */
    @Override
    public void onUpdate(int delta) {
        if(bonus.isActivated() && bonus.isOver(delta))
        {
            bonus.deactivate();

            // remove bonus from map
            getMediator().removeManager(this);
        }
    }

    /**
     * Returns this manager's actor.
     *
     * @return Actor
     */
    @Override
    public Actor getActor() {
        return bonus;
    }

    /**
     * Returns this bonus activate hitbox.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getKillingHitbox() {
        Rectangle2D[] hitbox = new Rectangle2D[1];
        hitbox[0] = new Rectangle2D(bonus.getLocation(), bonus.getWidth(), bonus.getHeight());
        return hitbox;
    }

    /**
     * Returns this manager's lethal hitbox. That means when this hitbox hits an killing hitbox
     * it applies an effect on this actor.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getlethalHitbox() {
        return new Rectangle2D[0];
    }

    /**
     * Reset this actor.
     */
    @Override
    public void reset() {
    }
}
