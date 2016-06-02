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
package ActorManager;

import Models.Actor.Actor;
import Models.world.Rectangle2D;
import mediator.Mediator;
import player.Player;

/**
 * @author durza9390
 */
public abstract class ActorManager {
    private Mediator mediator;
    private Player player;
    private boolean isAlive;

    public boolean isIsAlive() {
        return isAlive;
    }

    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
        isAlive = true;
    }

    public ActorManager(Mediator mediator) {
        this.mediator = mediator;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public abstract void onUpdate(int delta);

    public abstract Actor getActor();

    public abstract Rectangle2D[] getKillingHitbox();

    public abstract Rectangle2D[] getlethalHitbox();

    public abstract ActorManager getInstance();

    public Player getPlayer() {
        return player;
    }

    public Mediator getMediator() {
        return mediator;
    }
}
