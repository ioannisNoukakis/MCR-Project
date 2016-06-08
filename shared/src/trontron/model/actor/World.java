/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trontron.model.actor;

import trontron.model.world.Direction;
import trontron.model.world.Point2D;

/**
 *
 * @author durza9390
 */
public class World extends Actor{

    public World(int id, String name, Point2D location, float speed, Direction direction, int width, int height) {
        super(id, name, location, speed, direction, height, width);
    }
}
