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
package Actor;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import world.Direction;
import world.Point2D;

/**
 *
 * @author durza9390
 */
public class Projectile extends Actor {

    private Point2D point;
    private final int longueurProjectil = 10;
    private float timeToLive;
    
    public Projectile(String name, Point2D location, float speed, float angle, float timeToLive) {
        super(name, location, speed, Direction.noWhere, 0, 0);

        double length = Math.pow(Math.pow(longueurProjectil,2)+Math.pow(longueurProjectil,2),0.5);
        float xChange = (float)(length * Math.cos(Math.toRadians(angle)));
        float yChange = (float)(length * Math.sin(Math.toRadians(angle)));
        point = new Point2D(location.getX()+xChange, location.getY()+yChange);
        this.timeToLive = timeToLive;
    }

    @Override
    public void onRender(GameContainer container, Graphics g) {
        g.setLineWidth(2.5f);
        g.drawLine(super.getLocation().getX(), super.getLocation().getY(), point.getX(), point.getY());
    }

    @Override
    public void onUpdate(GameContainer container, int delta) {
        float diffX = (float)Math.abs(super.getLocation().getX() - point.getX());
        float diffY = (float)Math.abs(super.getLocation().getY() - point.getY());
        super.setLocation(point);
        point = new Point2D(point.getX() + diffX, point.getY() + diffY);
    }

    public float getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(float timeToLive) {
        this.timeToLive = timeToLive;
    }
}
