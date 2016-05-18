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

import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import world.Direction;
import world.Point2D;

/**
 *
 * @author durza9390
 */
public class Moto extends Actor {

    private Color color;
    private LinkedList<Point2D> tail;
    private int tailSize;
    private int diametre;
        
    public Moto(String name, Point2D location, float speed, int tailSize, Color color, int diametre) {
        super(name, location, speed, Direction.noWhere);
        this.color = color;
        this.tailSize = tailSize;
        tail = new LinkedList<>();
        this.diametre = diametre;
    }

    @Override
    public void onRender(GameContainer container, Graphics g) {
        g.setColor(color);
        g.fillOval(super.getLocation().getX(), super.getLocation().getY(), diametre, diametre);
        
        for(int i = 0; i < tail.size()-1; i++)
        {
            g.drawLine(tail.get(i).getX(), tail.get(i).getY(),tail.get(i+1).getX(), tail.get(i+1).getY());
        }
    }
    
    @Override
    public void onUpdate(GameContainer container, int delta) {
        
        if(tail.size() > tailSize)
            tail.removeFirst();
        
        tail.add(new Point2D(super.getLocation().getX()+diametre/2, super.getLocation().getY()+diametre/2));
        
        switch (super.getDirection()) {
            case right:
                super.setLocation(new Point2D(super.getLocation().getX()+super.getSpeed()*(float)delta,
                        super.getLocation().getY()));
                break;
            case left:
                super.setLocation(new Point2D(super.getLocation().getX()-super.getSpeed()*(float)delta,
                        super.getLocation().getY()));
                break;
            case up:
                super.setLocation(new Point2D(super.getLocation().getX(),
                        super.getLocation().getY()-super.getSpeed()*(float)delta));
                break;
            case down:
                super.setLocation(new Point2D(super.getLocation().getX(),
                        super.getLocation().getY()+super.getSpeed()*(float)delta));
                break;
        }
    }
}
