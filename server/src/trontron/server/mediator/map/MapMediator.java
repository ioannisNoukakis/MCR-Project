package trontron.server.mediator.map;

import trontron.model.actor.bonus.Bonus;
import trontron.model.actor.bonus.SpeedBonus;
import trontron.model.actor.bonus.SuperSizeMe;
import trontron.protocol.message.UpdateWorld;
import trontron.server.actor.manager.*;
import trontron.model.actor.Actor;
import trontron.model.world.Direction;
import trontron.model.world.Point2D;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;

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

    /**
     * The name of the map
     */
    private final String mapName;

    /**
     * The x size of the map
     */
    private final int maxX;

    /**
     * The y size of the map
     */
    private final int maxY;

    /**
     * The spawn frequency for bonuses
     */
    private final int spawnFrequency;

    /**
     * The maximal number of bonuses on the map
     */
    private final int maxSpawn;

    /**
     * The current number of bonuses on the map
     */
    private int currentBonusesSpawned;

    /**
     * The random generator used to set bonuses
     */
    private final Random random = new Random();

    /**
     * Constructor
     * @param mapName The name of the map
     * @param maxX The x size of the map
     * @param maxY The y size of the map
     * @param spawnFrequency The bonus spawn frequency
     * @param maxSpawn The maximal number of bonuses on the map
     */
    public MapMediator(String mapName, int maxX, int maxY, int spawnFrequency, int maxSpawn) {
        playableManagers = new CopyOnWriteArrayList<>();
        nonPlayableManagers = new CopyOnWriteArrayList<>();
        this.mapName = mapName;
        this.maxX = maxX;
        this.maxY = maxY;
        this.spawnFrequency = spawnFrequency;
        this.maxSpawn = maxSpawn;
    }

    /**
     * Gets the list of playable managers on the map
     * @return The list of managers
     */
    public List<PlayableManager> getPlayableManagers() {
        return playableManagers;
    }

    /**
     * Gets the list of non playable managers on the map
     * @return The list of managers
     */
    public List<NonPlayableManager> getNonPlayableManagers() {
        return nonPlayableManagers;
    }

    /**
     * Adds a manager to the map
     * @param manager The manager
     */
    public synchronized void addManager(ActorManager manager) {
        if (manager instanceof PlayableManager) {
            playableManagers.add((PlayableManager)manager);
        }
        else {
            nonPlayableManagers.add((NonPlayableManager)manager);

            // handle bonuses
            if (manager instanceof BonusManager) {
                currentBonusesSpawned++;
            }
        }
    }

    /**
     * Removes a manager from the map
     * @param manager The manager
     */
    public synchronized void removeManager(ActorManager manager) {
        if (manager instanceof PlayableManager) {
            playableManagers.remove(manager);
        }
        else {
            nonPlayableManagers.remove(manager);

            // handle bonuses
            if (manager instanceof BonusManager) {
                currentBonusesSpawned--;
            }
        }
    }

    /**
     * Detects a collision between a playable actor and the objects on the map
     * @param a The manager of the playable actor
     */
    public void verifyMove(PlayableManager a) {
        // detect collisions with non playable objects
        for (NonPlayableManager b : getNonPlayableManagers()) {
            if (Rectangle2D.areOverlapping(a.getlethalHitbox(), b.getKillingHitbox())) {
                b.handleCollision(a);
            }
        }
    }

    /**
     * Gets the x size of the map
     * @return The size
     */
    public int getMaxX() {
        return maxX;
    }

    /**
     * Gets the y size of the map
     * @return The size
     */
    public int getMaxY() {
        return maxY;
    }

    /**
     * Returns a random value between tho numbers
     * @param min The lower value
     * @param max The higher value
     * @return A number between the lowest and highest numbers
     */
    private float getRandomValue(float min, float max) {
        return (float)random.nextDouble() * (max - min) + min;
    }

    /**
     * Changes the map for a playable actor
     * @param playable The manager of the playable actor
     * @param m The mediator of the new map
     */
    public synchronized void ChangePlayableMap(PlayableManager playable, MapMediator m) {
        playableManagers.remove(playable);
        playable.setMediator(m);
        m.playableManagers.add(playable);
        playable.reset();
        playable.getActor().getLocation().setX(getRandomValue(playable.getActor().getWidth(), m.getMaxX() - playable.getActor().getWidth()));
        playable.getActor().getLocation().setY(getRandomValue(playable.getActor().getWidth(), m.getMaxY() - playable.getActor().getHeight()));
    }

    /**
     * Makes the map live
     */
    @Override
    public void run() {
        long time;
        int spawnCounter = 0;
        try {
            while (true) {
                time = System.currentTimeMillis();
                Thread.sleep(5);
                List<Actor> listActor = new LinkedList<>();
                
                for (ActorManager actorManager : playableManagers) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }
                
                for (ActorManager actorManager : nonPlayableManagers) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }

                // generate bonuses randomly
                spawnCounter += System.currentTimeMillis() - time;
                if(spawnFrequency != 0 && spawnCounter > spawnFrequency && currentBonusesSpawned < maxSpawn)
                {
                    currentBonusesSpawned++;
                    spawnCounter = 0;
                    BonusManager bonusManager = generateRandomBonus(listActor);
                    nonPlayableManagers.add(bonusManager);
                }

                for (PlayableManager playableManager : playableManagers) {
                    UpdateWorld get = new UpdateWorld(listActor, mapName);
                    playableManager.getPlayer().sendUDP(get);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates a random bonus on the map
     * @param listActors The list of concerned actors
     * @return The manager of the new bonus
     */
    private BonusManager generateRandomBonus(List<Actor> listActors)
    {
        List<MapBehaviour> behaviours = new LinkedList<>();
        behaviours.add(new MapBehaviour(this, (a, b) -> ((Bonus)b.getActor()).activate(a.getActor())));

        switch(random.nextInt(2))
        {
            case 0:
                return new BonusManager(this, behaviours,
                        new SuperSizeMe(10000, -1, "Supersize", new Point2D(random.nextInt(getMaxX()), random.nextInt(getMaxY())), 0, Direction.none, 30, 30));
            case 1:
                return new BonusManager(this, behaviours,
                        new SpeedBonus(10000, -1, "Speed", new Point2D(random.nextInt(getMaxX()), random.nextInt(getMaxY())), 0, Direction.none, 30, 30, listActors, 3));

            default:
                return null;
        }
    }
}
