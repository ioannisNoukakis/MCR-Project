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
package camera;

import world.Direction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author durza9390
 */
public class CameraManager {

    private float x;
    private float y;
    private final float speed;
    private final float worldBoundariesX;
    private final float worldBoundariesY;
    private final int width;
    private final int height;
    private Direction d;

    private final int space = 5;

    public CameraManager(float x, float y, float speed, float worldBoundariesX, float worldBoundariesY, int width, int height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.worldBoundariesX = worldBoundariesX;
        this.worldBoundariesY = worldBoundariesY;
        this.width = width;
        this.height = height;
        this.d = Direction.noWhere;
    }

    public void onRender(Graphics g) {
        g.translate(-x, -y);
    }

    public void onUpdate(int delta) {
        switch (d) {
            case up:
                if (y - speed >= 0) {
                    y -= speed*(delta/5);
                }
                break;
            case down:
                if (y + speed <= worldBoundariesY) {
                    y += speed*(delta/5);
                }
                break;
            case left:
                if (x - speed >= 0) {
                    x -= speed*(delta/5);
                }
                break;
            case right:
                if (x + speed <= worldBoundariesX) {
                    x += speed*(delta/5);
                }
                break;
        }
        
        if(y < 0)
            y = 0;
        
        if(x < 0)
            x = 0;
        
        if(x > worldBoundariesX)
            x = worldBoundariesX;
        
        if(y > worldBoundariesY)
            y = worldBoundariesY;
    }

    public void onUserInput(GameContainer container) {
        int newX = container.getInput().getMouseX();
        int newY = container.getInput().getMouseY();
        
        if (newX <= space && newY <= space) {
            d = Direction.upLeft;
        } else if (newX <= space && newY >= height - space) {
            d = Direction.downLeft;
        } else if (newY <= space && newX >= width - space) {
            d = Direction.upRight;
        } else if (newY >= height - space && newX >= width - space) {
            d = Direction.downRight;
        } else if (newX <= space) {
            d = Direction.left;
        } else if (newX >= width - space) {
            d = Direction.right;
        } else if (newY <= space) {
            d = Direction.up;
        } else if (newY >= height - space) {
            d = Direction.down;
        } else {
            d = Direction.noWhere;
        }
    }

    public void printState() {
        System.out.println("[INFO] x" + x + " y " + y);
    }
}
