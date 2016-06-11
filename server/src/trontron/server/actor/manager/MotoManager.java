/*
 * Copyright (C) 2016 durza9390
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.actor.Moto;
import trontron.model.world.Direction;

import trontron.model.world.Point2D;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Manages a moto.
 */
public class MotoManager extends PlayableManager {

    /**
     * The Moto of this MotoManager
     */
    private Moto moto;
    private int originalWidth, originalHeight;
    private float originalSpeed;

    /**
     * Constructor
     * @param mediator: the mediator in where this actor will be.
     * @param listBehaviour: list of Behaviour of this actor.
     * @param moto: the moto.
     */
    public MotoManager(MapMediator mediator, List<MapBehaviour> listBehaviour, Moto moto) {
        super(mediator, listBehaviour);
        this.moto = moto;
        originalWidth = moto.getWidth();
        originalHeight = moto.getHeight();
        originalSpeed = moto.getSpeed();
    }

    /**
     * Updates the location of the moto after a given time.
     *
     * @param delta: the time elapsed between two updates
     */
    @Override
    public void onUpdate(int delta) {

        if (moto.getSpeed() == 0)
            return;

        if (moto.getTail().size() > moto.getMaxTailSize())
            moto.getTail().removeFirst();

        moto.getTail().add(new Point2D(moto.getLocation().getX() + moto.getWidth() / 2, moto.getLocation().getY() + moto.getHeight() / 2));

        switch (moto.getDirection()) {
            case right:
                moto.setLocation(new Point2D(moto.getLocation().getX() + moto.getSpeed() * (float) delta / (float) 10.0,
                        moto.getLocation().getY()));
                break;
            case left:
                moto.setLocation(new Point2D(moto.getLocation().getX() - moto.getSpeed() * (float) delta / (float) 10.0,
                        moto.getLocation().getY()));
                break;
            case up:
                moto.setLocation(new Point2D(moto.getLocation().getX(),
                        moto.getLocation().getY() - moto.getSpeed() * (float) delta / (float) 10.0));
                break;
            case down:
                moto.setLocation(new Point2D(moto.getLocation().getX(),
                        moto.getLocation().getY() + moto.getSpeed() * (float) delta / (float) 10.0));
                break;
        }
        super.getMediator().verifyMove(this);
    }

    /**
     * Returns this manager's actor.
     *
     * @return Actor
     */
    @Override
    public Actor getActor() {
        return moto;
    }

    /**
     * Returns this manager's killing hitbox. That means when an other actor hits this
     * hitbox it applies an effect on him.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getlethalHitbox() {
        Rectangle2D[] hitbox = new Rectangle2D[1];
        hitbox[0] = new Rectangle2D(moto.getLocation(), moto.getWidth(), moto.getHeight());
        return hitbox;
    }

    /**
     * Returns this manager's lethal hitbox. That means when this hitbox hits an killing hitbox
     * it applies an effect on this manager's moto.
     *
     * @return Rectangle2D[]
     */
    @Override
    public Rectangle2D[] getKillingHitbox() {
        Rectangle2D[] hitbox = new Rectangle2D[moto.getTail().size()];

        for (int i = 0; i < moto.getTail().size() - 1; i++) {
            double distance = 0, epaisseur = 0;

            if (moto.getTail().get(i + 1).getX() == moto.getTail().get(i).getX()) {
                distance = Math.abs(moto.getTail().get(i + 1).getY() - moto.getTail().get(i).getY());
            } else {
                epaisseur = Math.abs(moto.getTail().get(i + 1).getX() - moto.getTail().get(i).getX());
            }
            hitbox[i] = new Rectangle2D(moto.getTail().get(i), distance, epaisseur);
        }
        return hitbox;
    }

    /**
     * reset the tail and the Direction of this moto.
     */
    @Override
    public void reset() {
        moto.getTail().clear();
        moto.setDirection(Direction.none);
        moto.setWidth(originalWidth);
        moto.setHeight(originalHeight);
        moto.setSpeed(originalSpeed);
    }
}
