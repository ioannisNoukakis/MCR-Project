package trontron.server.mediator;

import trontron.server.actor.manager.ActorManager;
import trontron.server.actor.manager.MotoManager;
import trontron.server.actor.manager.TeleporterManager;
import trontron.server.actor.manager.WorldManager;
import trontron.model.actor.Actor;
import trontron.model.actor.World;
import trontron.protocol.sync.GetWorldContents;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;
import trontron.model.world.Rectangle2D;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author durza9390
 */
public abstract class MediatorMap extends Thread {

    protected List<MotoManager> listMotoManager;
    protected List<TeleporterManager> listeTeleporterManager;
    protected List<WorldManager> listeWorld;
    private String mapName;
    private int maxX;
    private int maxY;
    private final Random random = new Random();
    
    public MediatorMap(String mapName, int maxX, int maxY) {
        listMotoManager = new CopyOnWriteArrayList<>();
        listeTeleporterManager = new CopyOnWriteArrayList<>();
        listeWorld = new CopyOnWriteArrayList<>();
        this.mapName = mapName;
        this.maxX = maxX;
        this.maxY = maxY;
        listeWorld.add(new WorldManager(new World(-2, mapName, new Point2D(0, 0),0, Direction.noWhere, maxX, maxY), this));
    }

    public synchronized void addMotoManager(MotoManager a) {
        listMotoManager.add(a);
    }
    
    public synchronized void addTeleporterManager(TeleporterManager a) {
        listeTeleporterManager.add(a);
    }
    
    public synchronized void addWorldElement(WorldManager a) {
        listeWorld.add(a);
    }

    public abstract void verifyMove(MotoManager a);

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

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    private float getRandomValue(float min, float max) {
        return (float)random.nextDouble() * (max - min) + min;
    }

    public synchronized void ChangeMotoMap(MotoManager moto, MediatorMap m) {
        listMotoManager.remove(moto);
        moto.setMediator(m);
        m.addMotoManager(moto);
        moto.reset();
        moto.getActor().getLocation().setX(getRandomValue(moto.getActor().getWidth(), m.getMaxX()-moto.getActor().getWidth()));
        moto.getActor().getLocation().setY(getRandomValue(moto.getActor().getWidth(), m.getMaxY()-moto.getActor().getHeight()));
        //moto.getActor().getLocation().setX((float)RUG.U(moto.getActor().getWidth(), m.getMaxX()-moto.getActor().getWidth()));
        //moto.getActor().getLocation().setY((float)RUG.U(moto.getActor().getWidth(), m.getMaxY()-moto.getActor().getHeight()));
    }

    @Override
    public void run() {
        long time;
        try {
            while (true) {
                time = System.currentTimeMillis();
                Thread.sleep(10);
                LinkedList<Actor> listActor = new LinkedList<>();
                
                for (ActorManager actorManager : listMotoManager) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }
                
                for (ActorManager actorManager : listeTeleporterManager) {
                    listActor.add(actorManager.getActor());
                }

                for (ActorManager actorManager : listMotoManager) {
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
