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
 * A rectangle
 */
public class Rectangle2D implements Serializable {
    /**
     * The position of the rectangle
     */
    private Point2D position;

    /**
     * The width of the rectangle
     */
    private double width;

    /**
     * The height of the rectangle
     */
    private double height;

    /**
     * Constructor
     * @param position The position of the rectangle
     * @param width The width
     * @param height The height
     */
    public Rectangle2D(Point2D position, double width, double height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the x position of the rectangle
     * @return The value of x
     */
    public float getX() {
        return position.getX();
    }

    /**
     * Gets the y position of the rectangle
     * @return The value of y
     */
    public float getY() {
        return position.getY();
    }

    /**
     * Gets the height of the rectangle
     * @return The height
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the rectangle
     * @return The width
     */
    public double getWidth() {
        return width;
    }

    /**
     * Checks if two rectangles are overlapping
     * @param a The first rectangle
     * @param b The second rectangle
     * @return True if the rectangles are overlapping, false otherwise
     */
    public static boolean areOverlapping(Rectangle2D[] a, Rectangle2D[] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == null || b[j] == null) {
                    return false;
                }

                if (a[i].getX() < b[j].getX() + b[j].getWidth()
                        && a[i].getX() + a[i].getWidth() > b[j].getX()
                        && a[i].getY() < b[j].getY() + b[j].getHeight()
                        && a[i].getY() + a[i].getHeight() > b[j].getY()) {
                    return true;
                }
            }
        }
        return false;
    }
}
