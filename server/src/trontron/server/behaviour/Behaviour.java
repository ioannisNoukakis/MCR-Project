package trontron.server.behaviour;

/**
 * Created by durza9390 on 09.06.16.
 */
public class Behaviour {
    private ICollision comportement;
    private String mapName;

    public Behaviour(ICollision comportement, String mapName) {
        this.comportement = comportement;
        this.mapName = mapName;
    }

    public ICollision getComportement() {
        return comportement;
    }

    public String getMapName() {
        return mapName;
    }
}
