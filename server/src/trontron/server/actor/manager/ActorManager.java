package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.world.Rectangle2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.MapMediator;

import java.util.List;

/**
 * Classe représentant les acteurs du jeu, c'est à dire les différentes entitées qui le composent
 * et qui peuvent intéragire entre elles.
 */
public abstract class ActorManager {
    private MapMediator mediator;
    private List<MapBehaviour> listComportement;

    public ActorManager(MapMediator mediator, List<MapBehaviour> listComportement) {
        this.mediator = mediator;
        this.listComportement = listComportement;
    }

    /**
     * Calcule l'état de l'acteur après un temps detla exprimé en millisecondes.
     *
     * @param delta: le nombre de millisecondes qui s'est écoulé entre deux updates
     */
    public abstract void onUpdate(int delta);

    /**
     * Retourne cet acteur.
     *
     * @return
     */
    public abstract Actor getActor();

    /**
     * Retourne la hitbox qui tue les autres acteur
     * 
     * @return 
     */
    public abstract Rectangle2D[] getKillingHitbox();

    /**
     * Retourne la hitbox qui tue cet acteur.
     * 
     * @return 
     */
    public abstract Rectangle2D[] getlethalHitbox();

    /**
     * Remet à zéro les paramètres de cet acteur.
     */
    public abstract void reset();

    /**
     * Retourne le médiateur dans le quel cet acteur se trouve.
     *
     * @return
     */
    public MapMediator getMediator() {
        return mediator;
    }

    /**
     * Change le médiateur dans le quel cet acteur se trouve.
     *
     * @param mediator
     */
    public void setMediator(MapMediator mediator) {
        this.mediator = mediator;
    }

    /**
     * Gère la collision entre deux acteurs.
     *
     * @param b
     */
    public void handleCollision(PlayableManager b) {
        for(MapBehaviour behaviour : listComportement) {
            if(behaviour.getMap().equals(mediator)) {
                behaviour.solveCollision(b, this);
            }
        }
    }
}
