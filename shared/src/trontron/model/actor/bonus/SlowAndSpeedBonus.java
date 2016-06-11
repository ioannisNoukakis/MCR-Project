package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author durza9390
 */
public class SlowAndSpeedBonus extends Bonus implements Serializable {
    private List<Actor> list;
    private int modifier;

    public SlowAndSpeedBonus(int timeToLive, int id, String name, Point2D location, float speed, Direction direction, int height, int width, List<Actor> list, int modifier) {
        super(timeToLive, id, name, location, speed, direction, height, width);
        this.list = list;
        this.modifier = modifier;
    }

    @Override
    public void activate(Actor me)
    {
        super.activate(me);
        for(Actor a : list)
        {
            if(a == me)
            {
                a.setSpeed(a.getSpeed()+modifier);
            }
            else
                a.setSpeed(a.getSpeed()-modifier);
        }
    }

    @Override
    public void desactivate()
    {
        for(Actor a : list)
        {
            a.setSpeed(a.getBaseSpeed());
        }
    }
}
