/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playerManager;

import java.util.List;
import world.Direction;

/**
 *
 * @author durza9390
 */
public class inputMediator {
    List<Player> playerList;

    public inputMediator(List<Player> playerList) {
        this.playerList = playerList;
    }
    
    public void onInput(char c)
    {
        for(Player p : playerList)
        {
            if(p.isItLeftKey(c))
            {
                p.setActorDirection(Direction.left);
            }
            if(p.isItRightKey(c))
            {
                p.setActorDirection(Direction.right);
            }
            if(p.isItUpKey(c))
            {
                p.setActorDirection(Direction.up);
            }
            if(p.isItDownKey(c))
            {
                p.setActorDirection(Direction.down);
            }
        }
    }
    
}
