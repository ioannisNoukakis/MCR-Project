package mediator;

import ActorManager.ActorManager;
import Models.world.Rectangle2D;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public abstract class Mediator extends Thread {
    protected LinkedList<ActorManager> listActorManager;

    public Mediator() {
        this.listActorManager = new LinkedList<>();
    }

    public synchronized void addActorManager(ActorManager a) {
        listActorManager.add(a);
    }

    public abstract void verifyMove(ActorManager a);

    public boolean checkCollision(Rectangle2D[] a, Rectangle2D[] b) {

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == null || b[i] == null)
                    return false;

                if (a[i].getX() < b[i].getX() + b[i].getWidth()
                        && a[i].getX() + a[i].getWidth() > b[i].getX()
                        && a[i].getY() < b[i].getY() + b[i].getHeight()
                        && a[i].getY() + a[i].getHeight() > b[i].getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    public LinkedList<ActorManager> getListActorManager() {
        return listActorManager;
    }

    public int getMaxX() {
        return 100;
    }

    public int getMaxY() {
        return 100;
    }
}
