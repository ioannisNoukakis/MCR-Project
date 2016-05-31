/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediator;

import ActorManager.MotoManager;
import Models.Actor.Moto;
import Models.Protocol.Connection.GetIdentity;
import Models.world.Point2D;
import player.Player;
import Models.Protocol.Connection.JoinGameFrame;
import Models.Protocol.Sync.ClientUpdate;
import Models.Protocol.Sync.GetWorldContents;
import UDP.Sender;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

/**
 *
 * @author durza9390
 */
public class HyperMediator {

    private LinkedList<Player> playerList;
    private MediatorLobby lobby = new MediatorLobby();

    public HyperMediator() {
        playerList = new LinkedList<>();
        lobby.start();
    }

    public synchronized void parseClientInput(Sender UDPsender, ObjectOutputStream out, Object o) throws Exception {
        //connection
        if (o.getClass() == JoinGameFrame.class) {
            JoinGameFrame joinGameFrame = (JoinGameFrame) o;
            MotoManager motoManager = new MotoManager(new Moto(playerList.size(), "", new Point2D(lobby.getMaxX() / 2, lobby.getMaxY() / 2), (float) 5, 30, 30, 100), lobby);
            playerList.add(new Player(
                    motoManager,
                    playerList.size(),
                    joinGameFrame.getName(),
                    UDPsender));
            motoManager.setPlayer(playerList.getLast());
            lobby.addActorManager(motoManager);
            System.out.println("A new client has connected: " + (playerList.size() - 1));
            out.reset();
            out.writeObject(new GetIdentity(playerList.size() - 1));
            out.flush();
        } else if (o.getClass() == ClientUpdate.class) {
            //update de la logique du jeu sous modification du client
            ClientUpdate clientUpdate = (ClientUpdate) o;
            playerList.get(clientUpdate.getWhoami()).getActorManager().getActor().setDirection(clientUpdate.getDir());
        }
    }
}
