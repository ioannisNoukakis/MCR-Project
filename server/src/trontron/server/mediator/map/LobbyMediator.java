package trontron.server.mediator.map;

/**
 * The mediator for the lobby map
 */
public class LobbyMediator extends MapMediator {
    /**
     * Constructor
     * @param mapName The name of the map
     * @param maxX The x size of the map
     * @param maxY The y size of the map
     * @param spawnFrequency The bonus spawn frequency
     * @param maxSpawn The maximal number of bonuses on the map
     */
    public LobbyMediator(String mapName, int maxX, int maxY, int spawnFrequency, int maxSpawn) {
        super(mapName, maxX, maxY, spawnFrequency, maxSpawn);
    }
}
