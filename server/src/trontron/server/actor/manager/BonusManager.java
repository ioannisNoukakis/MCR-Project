package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.bonus.Bonus;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.Behaviour;
import trontron.server.mediator.MediatorMap;

import java.util.LinkedList;


public class BonusManager extends NonPlayableManager {
    private Bonus bonus;

    public BonusManager(MediatorMap mediator, LinkedList<Behaviour> listComportement, Bonus bonus) {
        super(mediator, listComportement);
        this.bonus = bonus;
    }

    @Override
    public void onUpdate(int delta) {
        if(bonus.isActivated() && bonus.isOver(delta))
        {
            bonus.desactivate();
            getMediator().getListNonPlayableManager().remove(this);
            getMediator().setNbOfBonusSpawned(getMediator().getNbOfBonusSpawned()-1);
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
