package trontron.server.mediator;

import trontron.model.actor.World;
import trontron.server.actor.manager.*;
import trontron.model.actor.Moto;
import trontron.model.actor.Teleporter;
import trontron.model.world.Point2D;
import trontron.server.behaviour.MapBehaviour;
import trontron.server.mediator.map.LobbyMediator;
import trontron.server.mediator.map.MainMapMediator;
import trontron.server.mediator.map.InvertedMapMediator;
import trontron.server.player.Player;
import trontron.protocol.message.JoinGame;
import trontron.protocol.message.ChangeDirection;
import trontron.model.world.Direction;
import trontron.server.thread.ClientHandler;

import java.io.InputStream;
import java.util.*;

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
     * The lobby mainMap
     */
    private LobbyMediator lobby;

    /**
     * The main mainMap
     */
    private MainMapMediator mainMap;

    /**
     * The inverted map
     */
    private InvertedMapMediator invertedMap;

    /**
     * Constructor
     * @throws Exception If the maps can not be loaded
     */
    public HyperMediator() throws Exception{
        playerList = new ArrayList();

        // load lobby map properties
        Properties lobbyProperties = new Properties();
        InputStream lobbyStream = this.getClass().getClassLoader().getResourceAsStream("resources/lobby.properties");
        lobbyProperties.load(lobbyStream);
        final String lobbyName = lobbyProperties.getProperty("name");
        final int lobbyMaxX = Integer.parseInt(lobbyProperties.getProperty("maxX"));
        final int lobbyMaxY = Integer.parseInt(lobbyProperties.getProperty("maxY"));

        // load main map properties
        Properties mainMapProperties = new Properties();
        InputStream mapStream = this.getClass().getClassLoader().getResourceAsStream("resources/normalMap.properties");
        mainMapProperties.load(mapStream);
        final String mainMapName = mainMapProperties.getProperty("name");
        final int mainMapMaxX = Integer.parseInt(mainMapProperties.getProperty("maxX"));
        final int mainMapMaxY = Integer.parseInt(mainMapProperties.getProperty("maxY"));

        // load inverted map properties
        Properties invertedMapProperties = new Properties();
        InputStream invertedStream = this.getClass().getClassLoader().getResourceAsStream("resources/invertedMap.properties");
        invertedMapProperties.load(invertedStream);
        final String invertedMapName = invertedMapProperties.getProperty("name");
        final int invertedMapMaxX = Integer.parseInt(invertedMapProperties.getProperty("maxX"));
        final int invertedMapMaxY = Integer.parseInt(invertedMapProperties.getProperty("maxY"));

        // lobby map & world
        lobby = new LobbyMediator(lobbyName, lobbyMaxX, lobbyMaxY, 0, 0);
        World lobbyWorld = new World(-1, lobbyName, new Point2D(0, 0), 0, Direction.none, lobbyMaxX, lobbyMaxY);

        // main map & world
        mainMap = new MainMapMediator(mainMapName, mainMapMaxX, mainMapMaxY, 10000, 10);
        World mainMapWorld = new World(-2, mainMapName, new Point2D(0, 0), 0, Direction.none, mainMapMaxX, mainMapMaxY);

        // inverted map & world
        invertedMap = new InvertedMapMediator(invertedMapName, invertedMapMaxX, invertedMapMaxY, 10000, 10);
        World invertedMapWorld = new World(-3, invertedMapName, new Point2D(0, 0), 0, Direction.none, invertedMapMaxX, invertedMapMaxY);

        // add world managers
        lobby.addManager(new WorldManager(lobby, getDefaultWorldBehaviour(), lobbyWorld));
        mainMap.addManager(new WorldManager(mainMap, getDefaultWorldBehaviour(), mainMapWorld));
        invertedMap.addManager(new WorldManager(invertedMap, getDefaultWorldBehaviour(), invertedMapWorld));

        // teleporter
        lobby.addManager(new TeleporterManager(lobby, getDefaultTeleporterBehaviour(),
                new Teleporter(-4, "Map normale", new Point2D(300, 300), 0, Direction.none, 40, 40), mainMap));
        lobby.addManager(new TeleporterManager(lobby, getDefaultTeleporterBehaviour(),
                new Teleporter(-5, "Map inversée", new Point2D(300, 600), 0, Direction.none, 40, 40), invertedMap));

        lobby.start();
        mainMap.start();
        invertedMap.start();
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
        MotoManager motoManager = new MotoManager(lobby, getDefaultMotoBehaviours(), m);

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
        player.getManager().getActor().setDirection(directionInformations.getNewDirection());
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

            // remove player from mainMap
            player.getManager().getMediator().removeManager(player.getManager());

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
     * Default behaviours for motos
     * @return The behaviours
     */
    private List<MapBehaviour> getDefaultMotoBehaviours()
    {
        return Arrays.asList(
                new MapBehaviour(lobby, (a, b) -> { }),
                new MapBehaviour(mainMap, (a, b) -> {
                    a.getMediator().ChangePlayableMap((PlayableManager)a, lobby);
                    a.reset();
                    a.getActor().setKills(0);
                    b.getActor().setKills(b.getActor().getKills()+1);
                }),
                new MapBehaviour(invertedMap, (a, b) -> {
                    a.getMediator().ChangePlayableMap((PlayableManager)a, lobby);
                    a.reset();
                    a.getActor().setKills(0);
                    b.getActor().setKills(b.getActor().getKills()-1);
                })
        );
    }

    /**
     * Default behaviours for teleporters
     * @return The behaviours
     */
    private List<MapBehaviour> getDefaultTeleporterBehaviour()
    {
        return Arrays.asList(
            new MapBehaviour(lobby, (a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, ((TeleporterManager)b).getMediatorDeDestination())),
            new MapBehaviour(mainMap, (a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, ((TeleporterManager)b).getMediatorDeDestination())),
            new MapBehaviour(invertedMap, (a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, ((TeleporterManager)b).getMediatorDeDestination()))
        );
    }

    /**
     * Default behaviours for worlds
     * @return The behaviours
     */
    private List<MapBehaviour> getDefaultWorldBehaviour() {
        return Arrays.asList(
            new MapBehaviour(lobby, (a, b) -> {
                a.getActor().setLocation(new Point2D(a.getMediator().getMaxX()/2, a.getMediator().getMaxY()/2));
                a.reset();
            }),
            new MapBehaviour(mainMap, (a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, lobby)),
            new MapBehaviour(invertedMap, (a, b) -> a.getMediator().ChangePlayableMap((PlayableManager)a, lobby))
        );
    }
}
/**a.getActor().setKills(0);
 b.getActor().setKills(b.getActor().getKills()+1);
 */