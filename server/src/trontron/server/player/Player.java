/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trontron.server.player;

import trontron.server.actor.manager.ActorManager;
import trontron.server.thread.ClientHandler;

/**
 * @author durza9390
 */
public class Player {
    private ActorManager a;
    private int id;
    private String name;
    private int nbKills;
    private ClientHandler handler;

    public Player(ActorManager a, int id, String name, ClientHandler handler) {
        this.a = a;
        this.id = id;
        this.name = name;
        this.handler = handler;
    }

    public void addKill() {
        nbKills++;
    }

    public int getNbKills() {
        return nbKills;
    }

    public void send(Object o) {
        handler.send(o);
    }

    public ActorManager getActorManager() {
        return a;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
