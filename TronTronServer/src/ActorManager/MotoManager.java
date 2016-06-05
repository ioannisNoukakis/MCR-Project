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
package ActorManager;

import Models.Actor.*;
import Models.world.Direction;

import static Models.world.Direction.down;
import static Models.world.Direction.left;
import static Models.world.Direction.right;
import static Models.world.Direction.up;

import Models.world.Point2D;
import Models.world.Rectangle2D;
import mediator.MediatorMap;

/**
 * @author durza9390
 */
public class MotoManager extends ActorManager {

    private Moto moto;

    public MotoManager(Moto moto, MediatorMap mediator) {
        super(mediator);
        this.moto = moto;
    }

    @Override
    public void onUpdate(int delta) {

        if (moto.getSpeed() == 0)
            return;

        if (moto.getTail().size() > moto.getTailSize())
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

    @Override
    public Actor getActor() {
        return moto;
    }

    @Override
    public Rectangle2D[] getlethalHitbox() {
        Rectangle2D[] hitbox = new Rectangle2D[1];
        hitbox[0] = new Rectangle2D(moto.getLocation(), moto.getWidth(), moto.getHeight());
        return hitbox;
    }

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

    @Override
    public void reset() {
        moto.getTail().clear();
        moto.setDirection(Direction.noWhere);
    }
}
