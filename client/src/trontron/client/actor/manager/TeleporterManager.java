package trontron.client.actor.manager;

import trontron.model.actor.Teleporter;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * How a teleporter should be displayed.
 */
public class TeleporterManager extends ActorManager{

    private Teleporter teleporter;
    private Color color;

    public TeleporterManager(Teleporter teleporter, Color color) {
        this.teleporter = teleporter;
        this.color = color;
    }

    /**
     * Render this teleporter into the game.
     *
     * @param container: the game container.
     * @param g: the graphics in which we will draw the teleporter.
     * @param font: the font that will be used to write this teleporter's name.
     */
    @Override
    public void onRender(GameContainer container, Graphics g, TrueTypeFont font) {
        g.setColor(color);
        font.drawString(teleporter.getLocation().getX()-50, teleporter.getLocation().getY()-50, teleporter.getName(), color);
        g.fillRect(teleporter.getLocation().getX(), teleporter.getLocation().getY(), teleporter.getWidth(), teleporter.getHeight());
    }
}
