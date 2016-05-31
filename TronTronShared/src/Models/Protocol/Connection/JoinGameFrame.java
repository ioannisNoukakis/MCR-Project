/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Protocol.Connection;

import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public class JoinGameFrame implements Serializable{
    private String name;

    public JoinGameFrame(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
