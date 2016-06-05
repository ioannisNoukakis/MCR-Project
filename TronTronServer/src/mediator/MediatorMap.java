package mediator;

import ActorManager.ActorManager;
import ActorManager.WorldManager;
import MAth.RandomUniformGenerator;
import Models.Actor.Actor;
import Models.Actor.World;
import Models.Protocol.Sync.GetWorldContents;
import Models.world.Direction;
import Models.world.Point2D;
import Models.world.Rectangle2D;

import java.util.LinkedList;

/**
 * @author durza9390
 */
public abstract class MediatorMap extends Thread {

    protected LinkedList<ActorManager> listActorManager;
    private String mapName;
    private int maxX;
    private int maxY;
    private RandomUniformGenerator RUG;
    
    public MediatorMap(String mapName, int maxX, int maxY) {
        this.listActorManager = new LinkedList<>();
        this.mapName = mapName;
        this.maxX = maxX;
        this.maxY = maxY;
        listActorManager.add(new WorldManager(new World(-2, mapName, new Point2D(0, 0),0, Direction.noWhere, maxX, maxY), this));
        RUG = new RandomUniformGenerator(System.currentTimeMillis());
    }

    public synchronized void addActorManager(ActorManager a) {
        listActorManager.add(a);
    }

    public abstract void verifyMove(ActorManager a);

    public synchronized boolean checkCollision(Rectangle2D[] a, Rectangle2D[] b) {

        //collision avec les autres acteurs
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i] == null || b[j] == null) {
                    return false;
                }

                if (a[i].getX() < b[j].getX() + b[j].getWidth()
                        && a[i].getX() + a[i].getWidth() > b[j].getX()
                        && a[i].getY() < b[j].getY() + b[j].getHeight()
                        && a[i].getY() + a[i].getHeight() > b[j].getY()) {
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
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public synchronized void ChangeActorMap(ActorManager am, MediatorMap m) {
        listActorManager.remove(am);
        am.setMediator(m);
        m.addActorManager(am);
        am.reset();
        am.getActor().getLocation().setX((float)RUG.U(am.getActor().getWidth(), m.getMaxX()-am.getActor().getWidth()));
        am.getActor().getLocation().setY((float)RUG.U(am.getActor().getWidth(), m.getMaxY()-am.getActor().getWidth()));
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
