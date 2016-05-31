/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Protocol.Sync;

import Models.world.Direction;
import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public class ClientUpdate implements Serializable{
    private Direction dir;
    private int whoami;

    public ClientUpdate(Direction dir, int whoami) {
        this.dir = dir;
        this.whoami = whoami;
    }
    
    public Direction getDir() {
        return dir;
    }

    public int getWhoami() {
        return whoami;
    }
 
}
