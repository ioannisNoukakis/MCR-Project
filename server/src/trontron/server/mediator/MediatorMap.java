package trontron.server.mediator;

import trontron.model.actor.Playable;
import trontron.server.actor.manager.*;
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

    protected List<PlayableManager> listPlayableManager;
    protected List<NonPlayableManager> listNonPlayableManager;
    private String mapName;
    private int maxX;
    private int maxY;
    private final Random random = new Random();
    
    public MediatorMap(String mapName, int maxX, int maxY) {
        listPlayableManager = new CopyOnWriteArrayList<>();
        listNonPlayableManager = new CopyOnWriteArrayList<>();
        this.mapName = mapName;
        this.maxX = maxX;
        this.maxY = maxY;
        listNonPlayableManager.add(new WorldManager(new World(-1, mapName, new Point2D(0, 0),0, Direction.noWhere, maxX, maxY), this));
    }

    public synchronized void addPlayableManager(PlayableManager a) {
        listPlayableManager.add(a);
    }
    
    public synchronized void addNonPlayableManager(NonPlayableManager a) {
        listNonPlayableManager.add(a);
    }

    public abstract void verifyMove(PlayableManager a);

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

    public synchronized void ChangeMotoMap(PlayableManager playable, MediatorMap m) {
        listPlayableManager.remove(playable);
        playable.setMediator(m);
        m.addPlayableManager(playable);
        playable.reset();
        playable.getActor().getLocation().setX(getRandomValue(playable.getActor().getWidth(), m.getMaxX() - playable.getActor().getWidth()));
        playable.getActor().getLocation().setY(getRandomValue(playable.getActor().getWidth(), m.getMaxY() - playable.getActor().getHeight()));
    }

    @Override
    public void run() {
        long time;
        try {
            while (true) {
                time = System.currentTimeMillis();
                Thread.sleep(10);
                LinkedList<Actor> listActor = new LinkedList<>();
                
                for (ActorManager actorManager : listPlayableManager) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }
                
                for (ActorManager actorManager : listNonPlayableManager) {
                    listActor.add(actorManager.getActor());
                }

                for (PlayableManager playableManager : listPlayableManager) {
                    GetWorldContents get = new GetWorldContents(listActor, mapName);
                    playableManager.getPlayer().getUDPsender().sendTo(get);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
