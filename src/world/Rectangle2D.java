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
package world;

/**
 *
 * @author durza9390
 */
public class Rectangle2D {
    Point2D a;
    int width, height;

    public Rectangle2D(Point2D a, int width, int height) {
        this.a = a;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return a.getX();
    }
    
    public float getY() {
        return a.getY();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
