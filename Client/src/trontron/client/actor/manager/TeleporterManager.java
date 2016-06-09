package trontron.client.actor.manager;

import trontron.model.actor.Teleporter;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 *
 * @author durza9390
 */
public class TeleporterManager extends ActorManager{

    private Teleporter teleporter;
    private Color color;

    public TeleporterManager(Teleporter teleporter, Color color) {
        this.teleporter = teleporter;
        this.color = color;
    }
    
    @Override
    public void onRender(GameContainer container, Graphics g, TrueTypeFont font) {
        g.setColor(color);
        font.drawString(teleporter.getLocation().getX()-50, teleporter.getLocation().getY()-50, teleporter.getName(), color);
        g.fillRect(teleporter.getLocation().getX(), teleporter.getLocation().getY(), teleporter.getWidth(), teleporter.getHeight());
    }
}
