/*
 * Copyright (C) 2016 durza9390
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package trontron.server.actor.manager;

import trontron.model.actor.Actor;
import trontron.model.world.Rectangle2D;
import trontron.server.comportement.Comportement;
import trontron.server.mediator.MediatorMap;
import trontron.server.comportement.ICollision;

import java.util.LinkedList;

/**
 * Classe représentant les acteurs du jeu, c'est à dire les différentes entitées qui le composent
 * et qui peuvent intéragire entre elles.
 */
public abstract class ActorManager {
    private MediatorMap mediator;
    private LinkedList<Comportement> listComportement;

    public ActorManager(MediatorMap mediator, LinkedList<Comportement> listComportement) {
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
    public MediatorMap getMediator() {
        return mediator;
    }

    /**
     * Change le médiateur dans le quel cet acteur se trouve.
     *
     * @param mediator
     */
    public void setMediator(MediatorMap mediator) {
        this.mediator = mediator;
    }

    /**
     * Gère la collision entre deux acteurs.
     *
     * @param b
     */
    public void handleCollision(PlayableManager b) {
        for(Comportement comp : listComportement)
        {
            if(comp.getMapName().equals(mediator.getMapName()))
            {
                comp.getComportement().solveCollision(b, this);
            }
        }
    }
}
