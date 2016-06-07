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
package Models.Actor;

import Models.world.Direction;
import Models.world.Point2D;
import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public abstract class Actor implements Serializable{
    private int id;
    private String name;
    private Point2D location;
    private float speed;
    private final float BaseSpeed;
    private Direction direction;
    private int height;
    private int width;

    public Actor(int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.speed = speed;
        this.direction = direction;
        this.height = height;
        this.width = width;
        BaseSpeed = speed;
    }
    
    public int getId() {
        return id;
    }
            
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public Point2D getLocation() {
        return location;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getBaseSpeed() {
        return BaseSpeed;
    }
}
