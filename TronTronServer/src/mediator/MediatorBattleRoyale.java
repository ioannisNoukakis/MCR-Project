/*
 * HEIG-VD / MCR 
 * Ioannis Noukakis && Djomo Patrick Deslé
 * Laboratoire N°01
 * File : MediatorBattleRoyale.java
 */
package mediator;

import ActorManager.MotoManager;

/**
 *
 * @author durza9390
 */
public class MediatorBattleRoyale extends MediatorMap{

    public MediatorBattleRoyale(String mapName, int maxX, int maxY) {
        super(mapName, maxX, maxY);
    }

    @Override
    public void verifyMove(MotoManager a) {
        
    }
    
    
}
