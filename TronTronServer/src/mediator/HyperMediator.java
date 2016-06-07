package mediator;

import ActorManager.MotoManager;
import ActorManager.TeleporterManager;
import Models.Actor.Moto;
import Models.Actor.Teleporter;
import Models.Protocol.Connection.GetIdentity;
import Models.world.Point2D;
import player.Player;
import Models.Protocol.Connection.JoinGameFrame;
import Models.Protocol.Sync.ClientUpdate;
import Models.world.Direction;
import UDP.Sender;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Properties;

/**
 *
 * @author durza9390
 */
public class HyperMediator {

    private LinkedList<Player> playerList;
    private MediatorLobby lobby;
    private MediatorMapNormale mainMap;

    public HyperMediator() throws Exception{
        playerList = new LinkedList<>();
        
        //load maps propreties
        Properties prop = new Properties();
        
        prop.load(new FileInputStream("lobby.properties"));
        lobby = new MediatorLobby(prop.getProperty("name"), Integer.parseInt(prop.getProperty("maxX")), Integer.parseInt(prop.getProperty("maxY")));
        
        prop.load(new FileInputStream("normalMap.properties"));
        mainMap = new MediatorMapNormale(prop.getProperty("name"), Integer.parseInt(prop.getProperty("maxX")), Integer.parseInt(prop.getProperty("maxY")), lobby);
        
        lobby.addTeleporterManager(new TeleporterManager(new Teleporter(-1, "Tp vers mapNormal", new Point2D(300, 300), 0, Direction.noWhere, 40, 40),
            mainMap, lobby));
        lobby.start();
        mainMap.start();
    }

    public synchronized void parseClientInput(Sender UDPsender, ObjectOutputStream out, Object o) throws Exception {
        // Connection
        if (o.getClass() == JoinGameFrame.class) {
            JoinGameFrame joinGameFrame = (JoinGameFrame) o;
            MotoManager motoManager = new MotoManager(new Moto(playerList.size(), joinGameFrame.getName(), new Point2D(60, 60), (float) 5, 30, 30, 200), lobby);
            playerList.add(new Player(
                    motoManager,
                    playerList.size(),
                    joinGameFrame.getName(),
                    UDPsender));
            motoManager.setPlayer(playerList.getLast());
            lobby.addMotoManager(motoManager);
            System.out.println("A new client has connected: " + (playerList.size() - 1));
            out.reset();
            out.writeObject(new GetIdentity(playerList.size() - 1));
            out.flush();
        } else if (o.getClass() == ClientUpdate.class) {
            // Update de la logique du jeu sous modification du client
            ClientUpdate clientUpdate = (ClientUpdate) o;
            playerList.get(clientUpdate.getWhoami()).getActorManager().getActor().setDirection(clientUpdate.getDir());
        }
    }
}
