package trontron.server.mediator.map;

import trontron.model.world.Rectangle2D;
import trontron.server.actor.manager.PlayableManager;

/**
 * The mediator for the inverted map
 */
public class InvertedMapMediator extends MapMediator {
    /**
     * Constructor
     * @param mapName The name of the map
     * @param maxX The x size of the map
     * @param maxY The y size of the map
     * @param spawnFrequency The bonus spawn frequency
     * @param maxSpawn The maximal number of bonuses on the map
     */
    public InvertedMapMediator(String mapName, int maxX, int maxY, int spawnFrequency, int maxSpawn) {
        super(mapName, maxX, maxY, spawnFrequency, maxSpawn);
    }

    /**
     * Detects a collision between a playable actor and the objects on the map
     * @param a The manager of the playable actor
     */
    @Override
    public void verifyMove(PlayableManager a) {
        super.verifyMove(a);

        // detect collisions with non playable objects
        for (PlayableManager b : getPlayableManagers()) {
            if (a != b && Rectangle2D.areOverlapping(a.getlethalHitbox(), b.getKillingHitbox())) {
                b.handleCollision(a);
            }
        }
    }
}
