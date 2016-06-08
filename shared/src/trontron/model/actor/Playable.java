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

import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public class Playable extends Actor implements Serializable{
    private boolean isAlive;

    public Playable(int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(id, name, location, speed, direction, height, width);
        isAlive = true;
    }
    
    public void kill()
    {
        super.setSpeed(0);
        isAlive = false;
    }
    
    public void hallelujah()
    {
        super.setSpeed(super.getBaseSpeed());
        isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
