package trontron.protocol.message;

import trontron.model.actor.Actor;

import java.io.Serializable;
import java.util.List;

/**
 * Message sent to communicate the current state of the world
 */
public class UpdateWorld implements Serializable {
    /**
     * The list of actors in the current world
     */
    private List<Actor> actorList;

    /**
     * The name of the world map
     */
    private String mapName;

    /**
     * Constructor
     * @param actorList The list of actors in the world
     * @param mapName The name of the world map
     */
    public UpdateWorld(List<Actor> actorList, String mapName) {
        this.actorList = actorList;
        this.mapName = mapName;
    }

    /**
     * Gets the actors in the current world
     * @return A list of actors
     */
    public List<Actor> getActorList() {
        return actorList;
    }

    /**
     * Gets the name of the world map
     * @return The name of the map
     */
    public String getMapName() {
        return mapName;
    }
}
