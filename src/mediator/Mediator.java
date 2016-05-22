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
package mediator;

import Actor.Actor;
import Actor.Moto;
import java.util.LinkedList;
import org.newdawn.slick.Color;

/**
 *
 * @author durza9390
 */
public class Mediator {

    private LinkedList<Actor> listActor;

    public Mediator(LinkedList<Actor> listActor) {
        this.listActor = listActor;
    }

    //TODO IMPROVE THIS (O(n^2))
    public void verifyMove(Actor a) {
        for (Actor b : listActor) {
            if (a != b) {
                if(a.getClass() == Moto.class && b.getClass() == Moto.class)
                {
                    motoToMotoCollision((Moto)a, (Moto) b);
                }
            }
        }
    }

    private void motoToMotoCollision(Moto a, Moto b) {

        Moto m1 = (Moto) a;

        for (int i = 0; i < m1.getTail().size() - 1; i++) {
            double distance = 0, epaisseur = 0;

            if (m1.getTail().get(i + 1).getX() == m1.getTail().get(i).getX()) {
                distance = Math.abs(m1.getTail().get(i + 1).getY() - m1.getTail().get(i).getY());
            } else {
                epaisseur = Math.abs(m1.getTail().get(i + 1).getX() - m1.getTail().get(i).getX());
            }

            if (b.getLocation().getX() < m1.getTail().get(i).getX() + distance
                    && b.getLocation().getX() + b.getWidth() > m1.getTail().get(i).getX()
                    && b.getLocation().getY() < m1.getTail().get(i).getY() + epaisseur
                    && b.getLocation().getY() + b.getHeight() > m1.getTail().get(i).getY()) {
                b.setColor(Color.gray);
                b.setSpeed(0);
                b.getTail().clear();
                b.setTailSize(0);
            }
        }
    }
}
