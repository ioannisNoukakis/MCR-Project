package trontron.client.actor.manager;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import trontron.model.actor.bonus.Bonus;

/**
 * Created by durza9390 on 09.06.2016.
 */
public class BonusManager extends ActorManager {

    private Bonus bonus;
    private Color color;

    public BonusManager(Bonus bonus, Color color) {
        this.bonus = bonus;
        this.color = color;
    }

    @Override

    public void onRender(GameContainer container, Graphics g, TrueTypeFont font) {
        if(!bonus.isActivated()) {
            g.setColor(color);

            g.fillOval(bonus.getLocation().getX(), bonus.getLocation().getY() - 50, bonus.getHeight(), bonus.getWidth());
            font.drawString(bonus.getLocation().getX(), bonus.getLocation().getY(), bonus.getName(), color);
        }
    }
}
