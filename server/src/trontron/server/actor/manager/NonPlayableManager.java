package trontron.server.actor.manager;

import trontron.server.behaviour.Behaviour;
import trontron.server.mediator.MediatorMap;

import java.util.LinkedList;

/**
 * Created by durza9390 on 09.06.16.
 */
public abstract class NonPlayableManager extends ActorManager {
    public NonPlayableManager(MediatorMap mediator, LinkedList<Behaviour> listComportement) {
        super(mediator, listComportement);
    }
}
