package trontron.client.actor.manager;

import trontron.model.actor.Moto;
import trontron.model.world.Point2D;

import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * How a moto should be displayed.
 */
public class MotoManager extends ActorManager {

    private Moto moto;
    private Color color;

    public MotoManager(Moto moto, Color color) {
        this.moto = moto;
        this.color = color;
    }

    /**
     * Render this moto into the game.
     *
     * @param container: the game container.
     * @param g: the graphics in which we will draw the noto.
     * @param font: the font that will be used to write this noto's name.
     */
    @Override
    public void onRender(GameContainer container, Graphics g, TrueTypeFont font) {
        if (moto.isAlive()) {
            g.setColor(color);
            g.setLineWidth(4);

            List<Point2D> tail = moto.getTail();
            for (int i = 0; i < tail.size() - 1; ++i) {
                g.drawLine(tail.get(i).getX(), tail.get(i).getY(), tail.get(i + 1).getX(), tail.get(i + 1).getY());
            }
        } else {
            g.setColor(Color.gray);
        }

        font.drawString(moto.getLocation().getX(), moto.getLocation().getY() - 50, moto.getName(), color);
        g.fillRect(moto.getLocation().getX(), moto.getLocation().getY(), moto.getHeight(), moto.getWidth());
    }
}
