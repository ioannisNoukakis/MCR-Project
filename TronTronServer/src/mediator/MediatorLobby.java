/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediator;

import ActorManager.ActorManager;
import Models.Actor.Actor;
import Models.Protocol.Sync.GetWorldContents;
import java.util.LinkedList;

/**
 *
 * @author durza9390
 */
public class MediatorLobby extends Mediator {

    public synchronized void verifyMove(ActorManager a) {
        for (ActorManager b : super.getListActorManager()) {
            if (a != b && checkCollision(a.getlethalHitbox(), b.getKillingHitbox())) {
                a.setIsAlive(false);
            }
        }
    }

    @Override
    public void run() {
        long time;
        try {
            while (true) {
                time = System.currentTimeMillis();

                Thread.sleep(10);

                LinkedList<Actor> listActor = new LinkedList<>();
                for (ActorManager actorManager : listActorManager) {
                    actorManager.onUpdate((int) (System.currentTimeMillis() - time));
                    listActor.add(actorManager.getActor());
                }

                for (ActorManager actorManager : listActorManager) {
                    GetWorldContents get = new GetWorldContents(listActor, 
                            "ressources/map/theGrid.tmx");
                    actorManager.getPlayer().getUDPsender().sendTo(get);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
