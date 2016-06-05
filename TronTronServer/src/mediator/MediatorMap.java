package mediator;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import Models.Actor.Actor;
import Models.Protocol.Sync.GetWorldContents;
import Models.world.Rectangle2D;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public abstract class MediatorMap extends Thread {

    protected LinkedList<ActorManager> listActorManager;
    private String mapName;

    public MediatorMap(String mapName) {
        this.listActorManager = new LinkedList<>();
        this.mapName = mapName;
    }

    public synchronized void addActorManager(ActorManager a) {
        listActorManager.add(a);
    }

    public abstract void verifyMove(ActorManager a);

    public boolean checkCollision(Rectangle2D[] a, Rectangle2D[] b) {

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == null || b[i] == null) {
                    return false;
                }

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

    public synchronized LinkedList<ActorManager> getListActorManager() {
        return listActorManager;
    }

    public int getMaxX() {
        return 100;
    }

    public int getMaxY() {
        return 100;
    }

    public synchronized void ChangeActorMap(ActorManager am, MediatorMap m) {
        listActorManager.remove(am);
        am.setMediator(m);
        m.addActorManager(am);
        am.reset();
        am.getActor().getLocation().setX(m.getMaxX() / 2);
        am.getActor().getLocation().setY(m.getMaxY() / 2);
    }

    @Override
    public void run() {
        long time;
        try {
            while (true) {

                time = System.currentTimeMillis();
                Thread.sleep(10);
                LinkedList<Actor> listActor = new LinkedList<>();

                for (ActorManager actorManager : listActorManager) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }

                for (ActorManager actorManager : listActorManager) {
                    if (actorManager.getPlayer() != null) {
                        GetWorldContents get = new GetWorldContents(listActor, mapName);
                        actorManager.getPlayer().getUDPsender().sendTo(get);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
