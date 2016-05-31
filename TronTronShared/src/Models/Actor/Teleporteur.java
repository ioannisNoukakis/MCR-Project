/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.Actor;

import Models.world.Direction;
import Models.world.Point2D;
import java.io.Serializable;

/**
 *
 * @author durza9390
 */
public class Teleporteur extends Actor implements Serializable{

    public Teleporteur(int id, String name, Point2D location, float speed, Direction direction, int height, int width) {
        super(id, name, location, speed, direction, height, width);
    }

    @Override
    public Teleporteur getInstance() {
        return this;
    }
}
