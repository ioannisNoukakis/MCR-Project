package trontron.server.mediator.map;

import trontron.model.actor.bonus.Bonus;
import trontron.model.actor.bonus.SlowAndSpeedBonus;
import trontron.model.actor.bonus.SuperSizeMe;
import trontron.protocol.message.UpdateWorld;
import trontron.server.actor.manager.*;
import trontron.model.actor.Actor;
import trontron.model.actor.World;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.Behaviour;
import trontron.server.behaviour.ICollisionBehaviour;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Base class for map mediators
 */
public abstract class MapMediator extends Thread {
    /**
     * The list of playable managers
     */
    private List<PlayableManager> playableManagers;

    /**
     * The list of non playable managers
     */
    private List<NonPlayableManager> nonPlayableManagers;

    private final String mapName;
    private final int maxX;
    private final int maxY;
    private final Random random = new Random();
    private final int spawnFrequency;
    private final int maxSpawn;
    private int nbOfBonusSpawned;
    
    public MapMediator(String mapName, int maxX, int maxY, List<Behaviour> listComp, int spawnFrequency, int maxSpawn) {
        playableManagers = new CopyOnWriteArrayList<>();
        nonPlayableManagers = new CopyOnWriteArrayList<>();
        this.mapName = mapName;
        this.maxX = maxX;
        this.maxY = maxY;
        this.spawnFrequency = spawnFrequency;
        this.maxSpawn = maxSpawn;
        nonPlayableManagers.add(new WorldManager(this, listComp, new World(-1, mapName, new Point2D(0, 0),0, Direction.none, maxX, maxY)));
    }

    public List<PlayableManager> getPlayableManagers() {
        return playableManagers;
    }

    public List<NonPlayableManager> getNonPlayableManagers() {
        return nonPlayableManagers;
    }

    public synchronized void addManager(ActorManager manager) {
        if (manager instanceof PlayableManager) {
            playableManagers.add((PlayableManager)manager);
        }
        else {
            nonPlayableManagers.add((NonPlayableManager)manager);
        }
    }

    public synchronized void removeManager(ActorManager manager) {
        if (manager instanceof PlayableManager) {
            playableManagers.remove(manager);
        }
        else {
            nonPlayableManagers.remove(manager);
        }
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

    public String getMapName() {
        return mapName;
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

    public synchronized void ChangePlayableMap(PlayableManager playable, MapMediator m) {
        playableManagers.remove(playable);
        playable.setMediator(m);
        m.playableManagers.add(playable);
        playable.reset();
        playable.getActor().getLocation().setX(getRandomValue(playable.getActor().getWidth(), m.getMaxX() - playable.getActor().getWidth()));
        playable.getActor().getLocation().setY(getRandomValue(playable.getActor().getWidth(), m.getMaxY() - playable.getActor().getHeight()));
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
                
                for (ActorManager actorManager : playableManagers) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }
                
                for (ActorManager actorManager : nonPlayableManagers) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }

                //génération des bonus de manière aléatoire
                spawnCounter += System.currentTimeMillis() - time;
                if(spawnFrequency != 0 && spawnCounter > spawnFrequency && nbOfBonusSpawned < maxSpawn)
                {
                    nbOfBonusSpawned++;
                    spawnCounter = 0;
                    BonusManager bonusManager = generateRandomBonus(listActor);
                    nonPlayableManagers.add(bonusManager);
                }

                for (PlayableManager playableManager : playableManagers) {
                    UpdateWorld get = new UpdateWorld(listActor, mapName);
                    playableManager.getPlayer().send(get);
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
        LinkedList<Behaviour> comportement = new LinkedList<>();
        comportement.add(new Behaviour(new ICollisionBehaviour() {
            @Override
            public void solveCollision(ActorManager a, ActorManager b) {
                ((Bonus)b.getActor()).activate(a.getActor());
            }
        }, mapName));

        switch(random.nextInt(2))
        {
            case 0:
                return new BonusManager(this, comportement,
                        new SuperSizeMe(10000, -1, "supersize me", new Point2D(random.nextInt(getMaxX()), random.nextInt(getMaxY())), 0, Direction.none, 30, 30));
            case 1:
                return new BonusManager(this, comportement,
                        new SlowAndSpeedBonus(10000, -1, "speed", new Point2D(random.nextInt(getMaxX()), random.nextInt(getMaxY())), 0, Direction.none, 30, 30, listActors, 3));

            default:
                return null;
        }
    }
}
