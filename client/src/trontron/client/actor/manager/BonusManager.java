package trontron.client.actor.manager;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;
import trontron.model.actor.Actor;
import trontron.model.actor.bonus.Bonus;

/**
 * How a bonus should be displayed.
 */
public class BonusManager extends ActorManager {

    private Bonus bonus;
    private Color color;

    public BonusManager(Bonus bonus, Color color) {
        this.bonus = bonus;
        this.color = color;
    }

    /**
     * Render this bonus into the game.
     *
     * @param container: the game container.
     * @param g: the graphics in which we will draw the bonus.
     * @param font: the font that will be used to write this bonus's name.
     */
    @Override
    public void onRender(GameContainer container, Graphics g, TrueTypeFont font) {
        if(!bonus.isActivated()) {
            g.setColor(color);

            g.fillOval(bonus.getLocation().getX(), bonus.getLocation().getY() - 50, bonus.getHeight(), bonus.getWidth());
            font.drawString(bonus.getLocation().getX(), bonus.getLocation().getY(), bonus.getName(), color);
        }
    }
}
