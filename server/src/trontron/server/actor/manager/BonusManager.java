package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.bonus.Bonus;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;


public class BonusManager extends NonPlayableManager {
    private Bonus bonus;

    public BonusManager(MapMediator mediator, List<MapBehaviour> listComportement, Bonus bonus) {
        super(mediator, listComportement);
        this.bonus = bonus;
    }

    @Override
    public void onUpdate(int delta) {
        if(bonus.isActivated() && bonus.isOver(delta))
        {
            bonus.desactivate();

            // remove bonus from map
            getMediator().removeManager(this);
        }
    }

    @Override
    public Actor getActor() {
        return bonus;
    }

    @Override
    public Rectangle2D[] getKillingHitbox() {
        Rectangle2D[] hitbox = new Rectangle2D[1];
        hitbox[0] = new Rectangle2D(bonus.getLocation(), bonus.getWidth(), bonus.getHeight());
        return hitbox;
    }

    @Override
    public Rectangle2D[] getlethalHitbox() {
        return new Rectangle2D[0];
    }

    @Override
    public void reset() {
        //rien à faire le bonus est statique
    }
}
