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
package ActorManager;

import Models.Actor.*;
import Models.world.Point2D;
import java.util.LinkedList;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author durza9390
 */
public class MotoManager extends ActorManager {
    
    private Moto moto;
    private Color color;

    public MotoManager(Moto moto, Color color) {
        this.moto = moto;
        this.color = color;
    }
   
    @Override
    public void onRender(GameContainer container, Graphics g) {
        g.setColor(color);
        g.setLineWidth(1);
        g.fillRect(moto.getLocation().getX(), moto.getLocation().getY(), moto.getHeight(), moto.getWidth());
        
        LinkedList<Point2D> tail = moto.getTail();
        for(int i = 0; i < tail.size()-1; i++)
        {
            g.drawLine(tail.get(i).getX(), tail.get(i).getY(),tail.get(i+1).getX(), tail.get(i+1).getY());
        }
    }
}
