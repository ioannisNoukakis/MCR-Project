package trontron.server.mediator;

import trontron.model.actor.Playable;
import trontron.model.actor.bonus.Bonus;
import trontron.model.actor.bonus.SlowAndSpeedBonus;
import trontron.model.actor.bonus.SuperSizeMe;
import trontron.server.actor.manager.*;
import trontron.model.actor.Actor;
import trontron.model.actor.World;
import trontron.protocol.sync.GetWorldContents;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;
import trontron.model.world.Rectangle2D;
import trontron.server.comportement.Comportement;
import trontron.server.comportement.ICollision;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author durza9390
 */
public abstract class MediatorMap extends Thread {

    private List<PlayableManager> listPlayableManager;
    private List<NonPlayableManager> listNonPlayableManager;
    private String mapName;
    private int maxX;
    private int maxY;
    private final Random random = new Random();
    private int frequecySpawn;
    private int maxSpawn;
    private int nbOfBonusSpawned;
    
    public MediatorMap(String mapName, int maxX, int maxY, LinkedList<Comportement> listComp, int frequecySpawn, int maxSpawn) {
        listPlayableManager = new CopyOnWriteArrayList<>();
        listNonPlayableManager = new CopyOnWriteArrayList<>();
        this.mapName = mapName;
        this.maxX = maxX;
        this.maxY = maxY;
        this.frequecySpawn = frequecySpawn;
        this.maxSpawn = maxSpawn;
        listNonPlayableManager.add(new WorldManager(this, listComp, new World(-1, mapName, new Point2D(0, 0),0, Direction.noWhere, maxX, maxY)));
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

    public synchronized void ChangePlayableMap(PlayableManager playable, MediatorMap m) {
        listPlayableManager.remove(playable);
        playable.setMediator(m);
        m.addPlayableManager(playable);
        playable.reset();
        playable.getActor().getLocation().setX(getRandomValue(playable.getActor().getWidth(), m.getMaxX() - playable.getActor().getWidth()));
        playable.getActor().getLocation().setY(getRandomValue(playable.getActor().getWidth(), m.getMaxY() - playable.getActor().getHeight()));
    }

    public List<PlayableManager> getListPlayableManager() {
        return listPlayableManager;
    }

    public List<NonPlayableManager> getListNonPlayableManager() {
        return listNonPlayableManager;
    }

    public String getMapName() {
        return mapName;
    }

    @Override
    public void run() {
        long time;
        int spawnCounter = 0;
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
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }

                //génération des bonus de manière aléatoire
                spawnCounter += System.currentTimeMillis() - time;
                if(frequecySpawn != 0 && spawnCounter > frequecySpawn && nbOfBonusSpawned < maxSpawn)
                {
                    nbOfBonusSpawned++;
                    spawnCounter = 0;
                    BonusManager bonusManager = generateRandomBonus(listActor);
                    listNonPlayableManager.add(bonusManager);
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

    public void setNbOfBonusSpawned(int nbOfBonusSpawned) {
        this.nbOfBonusSpawned = nbOfBonusSpawned;
    }

    public int getNbOfBonusSpawned() {
        return nbOfBonusSpawned;
    }

    public BonusManager generateRandomBonus(LinkedList<Actor> listActors)
    {
        LinkedList<Comportement> comportement = new LinkedList<>();
        comportement.add(new Comportement(new ICollision() {
            @Override
            public void solveCollision(ActorManager a, ActorManager b) {
                ((Bonus)b.getActor()).activate(a.getActor());
            }
        },getMapName()));

        switch(random.nextInt(2))
        {
            case 0:
                return new BonusManager(this, comportement,
                        new SuperSizeMe(10000, -1, "supersize me", new Point2D(random.nextInt(getMaxX()), random.nextInt(getMaxY())), 0, Direction.noWhere, 30, 30));
            case 1:
                return new BonusManager(this, comportement,
                        new SlowAndSpeedBonus(10000, -1, "speed", new Point2D(random.nextInt(getMaxX()), random.nextInt(getMaxY())), 0, Direction.noWhere, 30, 30, listActors, 3));

            default:
                return null;
        }
    }
}
