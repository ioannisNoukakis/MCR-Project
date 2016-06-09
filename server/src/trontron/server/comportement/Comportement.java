package trontron.server.comportement;

/**
 * Created by durza9390 on 09.06.16.
 */
public class Comportement {
    private ICollision comportement;
    private String mapName;

    public Comportement(ICollision comportement, String mapName) {
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
