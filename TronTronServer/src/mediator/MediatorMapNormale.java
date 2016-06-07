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
package mediator;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import ActorManager.TeleporterManager;
import ActorManager.WorldManager;
import Models.world.Point2D;

/**
 * @author durza9390
 */
public class MediatorMapNormale extends MediatorMap {

    private MediatorLobby lobby;

    public MediatorMapNormale(String mapName, int maxX, int maxY, MediatorLobby lobby) {
        super(mapName, maxX, maxY);
        this.lobby = lobby;
    }

    @Override
    public void verifyMove(MotoManager a) {
        
        for (MotoManager b : listMotoManager) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                super.ChangeMotoMap(a, lobby);
            }
        }
        
        for(WorldManager b : listeWorld)
        {
            if(checkCollision(a.getlethalHitbox(), b.getKillingHitbox()))
                super.ChangeMotoMap(a, lobby);
        }
    }
}
