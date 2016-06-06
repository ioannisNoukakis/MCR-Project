package ActorManager;

import Models.Actor.*;
import Models.world.Point2D;

import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
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
    public void onRender(GameContainer container, Graphics g, TrueTypeFont font) {
        if(moto.isAlive())
        {
            g.setColor(color);
            g.setLineWidth(4);

            List<Point2D> tail = moto.getTail();
            for (int i = 0; i < tail.size() - 1; ++i) {
                g.drawLine(tail.get(i).getX(), tail.get(i).getY(), tail.get(i + 1).getX(), tail.get(i + 1).getY());
            }
        }
        else
            g.setColor(Color.gray);
        
        font.drawString(moto.getLocation().getX(), moto.getLocation().getY()-50, moto.getName(), color);
        g.fillRect(moto.getLocation().getX(), moto.getLocation().getY(), moto.getHeight(), moto.getWidth());
    }
}
