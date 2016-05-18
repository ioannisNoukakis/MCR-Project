/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerManager;

import Actor.Actor;
import world.Direction;
/**
 *
 * @author durza9390
 */
public class Player {
    private Actor actor;
    private String playerName;
    private char rightKey;
    private char leftKey;
    private char upKey;
    private char downKey;

    public Player(Actor actor, String playerName, char upKey, char rightKey, char downbKey, char leftKey) {
        this.actor = actor;
        this.playerName = playerName;
        this.rightKey = rightKey;
        this.leftKey = leftKey;
        this.upKey = upKey;
        this.downKey = downbKey;
    }
    
    public Direction getActorDirection()
    {
        return actor.getDirection();
    }
    
    public void setActorDirection(Direction d)
    {
        actor.setDirection(d);
    }
    
    public boolean isItRightKey(char c)
    {
        return c == rightKey;
    }
    
    public boolean isItLeftKey(char c)
    {
        return c == leftKey;
    }
    
    public boolean isItUpKey(char c)
    {
        return c == upKey;
    }
    
    public boolean isItDownKey(char c)
    {
        return c == downKey;
    }
}
