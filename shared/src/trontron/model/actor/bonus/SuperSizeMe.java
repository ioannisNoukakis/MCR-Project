package trontron.model.actor.bonus;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;

import java.io.Serializable;

/**
 * Created by durza9390 on 09.06.2016.
 */
public class SuperSizeMe extends Bonus implements Serializable {
    private Actor me;
    private int originalWith, originalHeight;

    public SuperSizeMe(int timeToLive, int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(timeToLive, id, name, location, speed, direction, height, width);
    }

    @Override
    public void activate(Actor a) {
        super.activate(a);
        me = a;
        originalWith = a.getWidth();
        originalHeight = a.getHeight();
        me.setWidth(me.getWidth()*2);
        me.setHeight(me.getHeight()*2);
    }

    @Override
    public void desactivate() {
        me.setWidth(originalWith);
        me.setHeight(originalHeight);
    }
}
