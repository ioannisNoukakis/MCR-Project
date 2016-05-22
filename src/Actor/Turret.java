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

import java.awt.Graphics2D;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import world.Direction;
import world.Point2D;

/**
 *
 * @author durza9390
 */
public class Turret extends Actor {
    
    private double angleCannon;
    private final int longueurCannon = 25;
    private Color baseColor, turretColor;

    public Turret(String name, Point2D location, float speed, int height, int width, Color baseColor, Color turretColor) {
        super(name, location, speed, Direction.noWhere, height, width);
        this.baseColor = baseColor;
        this.turretColor = turretColor;
    }

    @Override
    public void onRender(GameContainer container, Graphics g) {
        g.setColor(baseColor);
        g.fillOval(super.getLocation().getX(), super.getLocation().getY(), super.getWidth(), super.getHeight());
        g.setColor(turretColor);
        g.fillOval(super.getLocation().getX()+super.getWidth()/2-super.getWidth()/4, 
                super.getLocation().getY()+super.getHeight()/2-super.getHeight()/4,
                super.getWidth()/2,
                super.getHeight()/2);
        g.setLineWidth(3);
        
        double length = Math.pow(Math.pow(longueurCannon,2)+Math.pow(longueurCannon,2),0.5);
        float xChange = (float)(length * Math.cos(Math.toRadians(angleCannon)));
        float yChange = (float)(length * Math.sin(Math.toRadians(angleCannon)));
        
        g.drawLine(super.getLocation().getX()+super.getWidth()/2, 
                 super.getLocation().getY()+super.getHeight()/2,
                super.getLocation().getX()+super.getWidth()/2+xChange, 
                super.getLocation().getY()+super.getHeight()/2+yChange);
    }

    @Override
    public void onUpdate(GameContainer container, int delta) {
        switch (super.getDirection()) {
            case right:
                angleCannon = (angleCannon + super.getSpeed()*(float)delta/(float)20.0)%360;
                break;
            case left:
                angleCannon = (angleCannon - super.getSpeed()*(float)delta/(float)20.0)%360;
                break;
        }
    }
}
