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
import mediator.Mediator;
import world.Point2D;

/**
 *
 * @author durza9390
 */
public class Moto extends Actor {

    private LinkedList<Point2D> tail;
    private int tailSize;
    private Mediator motoMediator;
        
    public Moto(Mediator motoMediator, String name, Point2D location, float speed, int height, int width, Color color, int tailSize) {
        super(name, location, speed, Direction.noWhere, height, width, color);
        this.tailSize = tailSize;
        tail = new LinkedList<>();
        this.motoMediator = motoMediator;
    }

    @Override
    public void onRender(GameContainer container, Graphics g) {
        g.setColor(super.getColor());
        g.fillRect(super.getLocation().getX(), super.getLocation().getY(), super.getHeight(), super.getWidth());
        
        for(int i = 0; i < tail.size()-1; i++)
        {
            g.drawLine(tail.get(i).getX(), tail.get(i).getY(),tail.get(i+1).getX(), tail.get(i+1).getY());
        }
    }
    
    @Override
    public void move(GameContainer container, int delta) {
        
        if(super.getSpeed() == 0)
            return;
        
        if(tail.size() > tailSize)
            tail.removeFirst();
        
        tail.add(new Point2D(super.getLocation().getX()+super.getWidth()/2, super.getLocation().getY()+super.getHeight()/2));
        
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
        
        motoMediator.verifyMove(this);
    }

    @Override
    public void onDeath() {
        setColor(Color.gray);
        setSpeed(0);
        tail.clear();
        tailSize = 0;
    }

    public LinkedList<Point2D> getTail() {
        return tail;
    }
}
