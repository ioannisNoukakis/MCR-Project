package trontron.server.mediator;

import trontron.server.actor.manager.*;
import trontron.model.actor.Moto;
import trontron.model.actor.Teleporter;
import trontron.model.world.Point2D;
import trontron.server.behaviour.Behaviour;
import trontron.server.behaviour.ICollisionBehaviour;
import trontron.server.mediator.map.LobbyMediator;
import trontron.server.mediator.map.MainMapMediator;
import trontron.server.player.Player;
import trontron.protocol.message.JoinGame;
import trontron.protocol.message.ChangeDirection;
import trontron.model.world.Direction;
import trontron.server.thread.ClientHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * The main mediator
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
    private LobbyMediator lobby;

    /**
     * The main map
     */
    private MainMapMediator map;

    /**
     * Constructor
     * @throws Exception If the maps can not be loaded
     */
    public HyperMediator() throws Exception{
        playerList = new ArrayList();
        
        // load maps properties
        Properties prop = new Properties();

        // lobby map
        InputStream lobbyStream = this.getClass().getClassLoader().getResourceAsStream("resources/lobby.properties");
        prop.load(lobbyStream);
        lobby = new LobbyMediator(prop.getProperty("name"),
                Integer.parseInt(prop.getProperty("maxX")),
                Integer.parseInt(prop.getProperty("maxY")),
                generateComportementWorld(), 0, 0);

        // normal map
        InputStream mapStream = this.getClass().getClassLoader().getResourceAsStream("resources/normalMap.properties");
        prop.load(mapStream);
        map = new MainMapMediator(prop.getProperty("name"),
                Integer.parseInt(prop.getProperty("maxX")),
                Integer.parseInt(prop.getProperty("maxY")),
                generateComportementWorld(), 10000, 10,
                lobby);

        // teleporter
        lobby.addManager(new TeleporterManager(lobby, generateComportementTeleporter(),
                new Teleporter(-2, "Tp vers mapNormal", new Point2D(300, 300), 0, Direction.none, 40, 40), map));

        lobby.start();
        map.start();
    }

    /**
     * Adds a new player to the game
     * @param handler
     * @param joinGame
     */
    public synchronized int addPlayer(ClientHandler handler, JoinGame joinGame) {
        // get new player id
        int playerId = nextId++;

        // create new manager
        Moto m = new Moto(playerId, joinGame.getName(), new Point2D(60, 60), (float) 5, 30, 30, 200);
        MotoManager motoManager = new MotoManager(lobby, generateComportementMoto(), m);

        // create new player
        Player newPlayer = new Player(motoManager, playerId, joinGame.getName(), handler);
        motoManager.setPlayer(newPlayer);

        // add new player to list
        playerList.add(newPlayer);

        // player starts in lobby
        lobby.addManager(motoManager);

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
        if (player != null) {
            // remove player from list
            playerList.remove(player);

            // remove player from map
            player.getActorManager().getMediator().removeManager(player.getActorManager());

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

    /**
     * factorize comportements creation for moto actor
     *
     * @return
     */
    public LinkedList<Behaviour> generateComportementMoto()
    {
        LinkedList<Behaviour> rtn = new LinkedList<>();

        rtn.add(new Behaviour((a, b) -> { }, "theLobby.tmx"));
        rtn.add(new Behaviour((a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, lobby), "theMap.tmx"));

        return rtn;
    }

    /**
     * factorize comportements creation for teleporter actor
     *
     * @return
     */
    public LinkedList<Behaviour> generateComportementTeleporter()
    {
        LinkedList<Behaviour> rtn = new LinkedList<>();

        rtn.add(new Behaviour((a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, ((TeleporterManager)b).getMediatorDeDestination()), "theLobby.tmx"));
        rtn.add(new Behaviour((a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, ((TeleporterManager)b).getMediatorDeDestination()), "theMap.tmx"));

        return rtn;
    }

    /**
     * factorize comportements creation for world actor
     *
     * @return
     */
    public LinkedList<Behaviour> generateComportementWorld() {
        LinkedList<Behaviour> rtn = new LinkedList<>();

        rtn.add(new Behaviour(new ICollisionBehaviour() {
            @Override
            public void solveCollision(ActorManager a, ActorManager b) {
                a.getActor().setLocation(new Point2D(a.getMediator().getMaxX()/2, a.getMediator().getMaxY()/2));
                a.reset();
            }
        }, "theLobby.tmx"));
        rtn.add(new Behaviour((a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, lobby), "theMap.tmx"));

        return rtn;
    }
}
