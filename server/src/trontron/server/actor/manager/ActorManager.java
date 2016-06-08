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
import trontron.server.mediator.MediatorMap;
import trontron.server.player.Player;

/**
 * @author durza9390
 */
public abstract class ActorManager {
    private MediatorMap mediator;
    private Player player;

    public ActorManager(MediatorMap mediator) {
        this.mediator = mediator;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract void onUpdate(int delta);

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
    
    public abstract void reset();

    public Player getPlayer() {
        return player;
    }

    public MediatorMap getMediator() {
        return mediator;
    }

    public void setMediator(MediatorMap mediator) {
        this.mediator = mediator;
    }
}
