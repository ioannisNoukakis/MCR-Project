/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trontron.protocol.sync;

import trontron.model.actor.Actor;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author durza9390
 */
public class GetWorldContents implements Serializable{
    private LinkedList<Actor> actorList;
    private String mapName;

    public GetWorldContents(LinkedList<Actor> actorList, String mapName) {
        this.actorList = actorList;
        this.mapName = mapName;
    }

    public LinkedList<Actor> getActorList() {
        return actorList;
    }

    public String getMapName() {
        return mapName;
    }
}
