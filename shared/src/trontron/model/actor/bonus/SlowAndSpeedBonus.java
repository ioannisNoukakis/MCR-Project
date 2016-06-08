package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.util.LinkedList;

/**
 *
 * @author durza9390
 */
public class SlowAndSpeedBonus extends Bonus{
    private LinkedList<Actor> list; 
    private int modifier;

    public SlowAndSpeedBonus(LinkedList<Actor> list, int modifier, int timeToLive, Actor me, int id,
                             String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(timeToLive, me, id, name, location, speed, direction, height, width);
        this.list = list;
        this.modifier = modifier;
    }
    
    public void activate()
    {
        for(Actor a : list)
        {
            if(a == super.getMe())
            {
                a.setSpeed(a.getSpeed()-modifier);
            }
            else
                a.setSpeed(a.getSpeed()+modifier);
        }
    }
    
    public void deactivate()
    {
        for(Actor a : list)
        {
            a.setSpeed(a.getBaseSpeed());
        }
    }
}
