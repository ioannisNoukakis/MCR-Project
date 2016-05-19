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
package com.heigvd.mcr.Actors;

import com.heigvd.mcr.world.Direction;
import com.heigvd.mcr.world.Point2D;

/**
 *
 * @author durza9390
 */
public class Moto extends Actor {

    private Point2D[] tail;

    public Moto(String name, Point2D location, double speed, int tailSize) {
        super(name, location, speed);
        tail = new Point2D[tailSize];
    }

    public void move(Direction d, int delta) {
        switch (d) {
            case up:
                super.setLocation(new Point2D(super.getLocation().getX()-super.getSpeed()*(double)delta,
                        super.getLocation().getY()));
                break;
            case down:
                super.setLocation(new Point2D(super.getLocation().getX()+super.getSpeed()*(double)delta,
                        super.getLocation().getY()));
                break;
            case left:
                super.setLocation(new Point2D(super.getLocation().getX(),
                        super.getLocation().getY()-super.getSpeed()*(double)delta));
                break;
            case right:
                super.setLocation(new Point2D(super.getLocation().getX(),
                        super.getLocation().getY()-super.getSpeed()*(double)delta));
                break;
        }
    }
}
