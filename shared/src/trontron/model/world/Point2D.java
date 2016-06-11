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
package trontron.model.world;

import java.io.Serializable;

/**
 * A 2D point
 */
public class Point2D implements Serializable {

    /**
     * The x coordinate
     */
    private float x;

    /**
     * The y coordinate
     */
    private float y;

    /**
     * Constructor
     * @param x The x coordinate of the point
     * @param y The y coordinate of the point
     */
    public Point2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Gets the x coordinate of the point
     * @return
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y coordinate of the point
     * @return
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the value of the x coordinate
     * @param x The new value
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the value of the y coordinate
     * @param y The new value
     */
    public void setY(float y) {
        this.y = y;
    }
}
