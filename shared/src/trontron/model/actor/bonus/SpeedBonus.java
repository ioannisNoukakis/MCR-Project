package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;
import java.util.List;

/**
 * A bonus which changes the speed of an actor
 */
public class SpeedBonus extends Bonus implements Serializable {
    /**
     * The list of actors
     */
    private List<Actor> list;

    /**
     * The speed modifier of this bonus
     */
    private final int modifier;

    /**
     * Constructor
     * @param timeToLive The lifetime of the bonus
     * @param id The id of the actor
     * @param name The name of the actor
     * @param location The initial location
     * @param speed The initial speed
     * @param width The width
     * @param height The height
     * @param list
     * @param modifier
     */
    public SpeedBonus(int timeToLive, int id, String name, Point2D location, float speed, Direction direction, int height, int width, List<Actor> list, int modifier) {
        super(timeToLive, id, name, location, speed, direction, height, width);
        this.list = list;
        this.modifier = modifier;
    }

    /**
     * Activates the bonus
     * @param actor The concerned actor
     */
    @Override
    public void activate(Actor actor)
    {
        super.activate(actor);

        actor.setSpeed(actor.getSpeed()+modifier);
    }

    /**
     * Deactivates the bonus
     */
    @Override
    public void deactivate()
    {
        super.deactivate();
        getActor().setSpeed(getActor().getBaseSpeed());
    }
}
