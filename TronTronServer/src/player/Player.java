/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import ActorManager.ActorManager;
import UDP.Sender;
import java.io.ObjectOutputStream;

/**
 *
 * @author durza9390
 */
public class Player {
    private ActorManager a;
    private int id;
    private String name;
    private Sender UDPsender;

    public Player(ActorManager a, int id, String name, Sender UDPsender) {
        this.a = a;
        this.id = id;
        this.name = name;
        this.UDPsender = UDPsender;
    }

    public Sender getUDPsender() {
        return UDPsender;
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
