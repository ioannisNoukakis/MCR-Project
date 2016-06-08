package trontron.server.mediator;

import trontron.server.actor.manager.MotoManager;
import trontron.server.actor.manager.TeleporterManager;
import trontron.model.actor.Moto;
import trontron.model.actor.Teleporter;
import trontron.model.world.Point2D;
import trontron.server.player.Player;
import trontron.protocol.message.JoinGame;
import trontron.protocol.message.ChangeDirection;
import trontron.model.world.Direction;
import trontron.protocol.Sender;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author durza9390
 */
public class HyperMediator {

    /**
     * The id given to the next player
     */
    private int nextId = 0;

    /**
     * The list of active players
     */
    private List<Player> playerList;

    /**
     * The lobby map
     */
    private MediatorLobby lobby;

    /**
     * The main map
     */
    private MediatorMapNormale mainMap;

    public HyperMediator() throws Exception{
        playerList = new ArrayList();
        
        // load maps properties
        Properties prop = new Properties();

        // lobby map
        InputStream lobbyStream = this.getClass().getClassLoader().getResourceAsStream("lobby.properties");
        prop.load(lobbyStream);
        lobby = new MediatorLobby(prop.getProperty("name"), Integer.parseInt(prop.getProperty("maxX")), Integer.parseInt(prop.getProperty("maxY")));

        // normal map
        InputStream mapStream = this.getClass().getClassLoader().getResourceAsStream("normalMap.properties");
        prop.load(mapStream);
        mainMap = new MediatorMapNormale(prop.getProperty("name"), Integer.parseInt(prop.getProperty("maxX")), Integer.parseInt(prop.getProperty("maxY")), lobby);

        // teleporter
        lobby.addTeleporterManager(new TeleporterManager(new Teleporter(-1, "Tp vers mapNormal", new Point2D(300, 300), 0, Direction.noWhere, 40, 40), mainMap, lobby));

        lobby.start();
        mainMap.start();
    }

    /**
     * Adds a new player to the game
     * @param UDPsender
     * @param joinGame
     */
    public synchronized int addPlayer(Sender UDPsender, JoinGame joinGame) {
        // get new player id
        int playerId = nextId++;

        // create new manager
        MotoManager motoManager = new MotoManager(new Moto(playerId, joinGame.getName(), new Point2D(60, 60), (float) 5, 30, 30, 200), lobby);

        // create new player
        Player newPlayer = new Player(motoManager, playerId, joinGame.getName(), UDPsender);

        // add new player to list
        playerList.add(newPlayer);

        motoManager.setPlayer(newPlayer);
        lobby.addMotoManager(motoManager);

        System.out.println("A new player has connected : " + newPlayer.getName() + " (" + playerId + ")");

        // return new player id
        return playerId;
    }

    /**
     * Updates an existing player
     * @param directionInformations
     */
    public synchronized void updatePlayer(ChangeDirection directionInformations) {
        // find player
        Player player = findPlayer(directionInformations.getPlayerId());

        // update direction
        player.getActorManager().getActor().setDirection(directionInformations.getNewDirection());
    }

    /**
     * Removes a player from the game
     * @param playerId
     */
    public synchronized void removePlayer(int playerId) {
        // find player
        Player player = findPlayer(playerId);

        // remove player
        if (player != null) {
            playerList.remove(player);

            System.out.println("A player has been removed : " + player.getName() + " (" + playerId + ")");
        }
    }

    /**
     * Gets an existing player by its id
     * @param playerId
     * @return
     */
    private Player findPlayer(int playerId) {
        Player player = null;
        for (Player p : playerList) {
            if (p.getId() == playerId) {
                player = p;
                break;
            }
        }
        return player;
    }
}
