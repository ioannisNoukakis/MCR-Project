package trontron.client.actor.manager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * How an actor should be displayed.
 */
public abstract class ActorManager {

    /**
     * Render this actor into the game.
     *
     * @param container: the game container.
     * @param g: the graphics in which we will draw the actor.
     * @param font: the font that will be used to write this actor's name.
     */
     public abstract void onRender(GameContainer container, Graphics g, TrueTypeFont font);
}
