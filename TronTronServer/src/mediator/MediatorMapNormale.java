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

/**
 * @author durza9390
 */
public class MediatorMapNormale extends Mediator {

    public void verifyMove(ActorManager a) {
        // Envoyer au client qu'il est mort
        super.getListActorManager().stream().filter(b -> checkCollision(a.getlethalHitbox(), b.getKillingHitbox())).forEach(b -> {
            // Envoyer au client qu'il est mort
            System.out.println(a.getPlayer().getName() + " IS DEAD");
        });
    }

    @Override
    public synchronized void run() {
        for (ActorManager actorManager : super.getListActorManager()) {
            actorManager.onUpdate(0);
        }
    }
}
