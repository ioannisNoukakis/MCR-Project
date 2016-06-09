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
package trontron.model.actor;

import java.util.LinkedList;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public class Moto extends Playable implements Serializable {

    private LinkedList<Point2D> tail;
    private float maxTailSize;
    private float tailSize;
        
    public Moto(int id, String name, Point2D location, float speed, int height, int width, int tailSize) {
        super(id, name, location, speed, Direction.noWhere, height, width);
        this.maxTailSize = tailSize;
        tail = new LinkedList<>();
    }

    public LinkedList<Point2D> getTail() {
        return tail;
    }

    public float getTailSize() {
        return tailSize;
    }

    public void setTailSize(int tailSize) {
        this.tailSize = tailSize;
    }

    public void setMaxTailSize(int maxTailSize) {
        this.maxTailSize = maxTailSize;
    }

    public float getMaxTailSize() {
        return maxTailSize;
    }
}
