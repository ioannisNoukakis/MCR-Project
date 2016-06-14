package trontron.client.game;

import org.newdawn.slick.*;
import trontron.client.actor.manager.ActorManager;
import trontron.client.actor.manager.BonusManager;
import trontron.client.actor.manager.MotoManager;
import trontron.client.actor.manager.TeleporterManager;
import trontron.client.thread.ServerHandler;
import trontron.model.actor.Actor;
import trontron.model.actor.Moto;
import trontron.model.actor.Teleporter;
import trontron.model.actor.bonus.Bonus;
import trontron.client.camera.CameraManager;
import trontron.client.player.Player;
import trontron.client.player.InputManager;
import trontron.protocol.message.UpdateWorld;

import org.newdawn.slick.tiled.TiledMap;

import java.awt.Font;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * This is the main class of the game. This is where the logic and render of the game loop is running.
 */
public class WindowGame extends BasicGame {

    private TiledMap map;
    private CameraManager camera;
    private Player player;
    private List<ActorManager> listActorManager = new CopyOnWriteArrayList<>();
    private LinkedList<Actor> scoreBoardList;
    private InputManager inputManager;
    private ServerHandler serverHandler;
    private String mapName;
    private TrueTypeFont font;
    private boolean mapHasChanged = false;

    public WindowGame(String playerName, String hostname, int port, int recieverPort) throws Exception {
        super("Trontron");

        // create server handler
        serverHandler = new ServerHandler(hostname, port, recieverPort, this, playerName);

        // create player
        player = new Player('w', 'd', 's', 'a');
        player.setId(serverHandler.getPlayerId());

        // todo : do better
        inputManager = new InputManager(player);

        // start listening for server messages
        serverHandler.start();

        int miniLogicSleep = 5;
        int maxLogicSleep = 20;
        AppGameContainer app = new AppGameContainer(this);

        app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), false);
        app.setVSync(true);
        app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(miniLogicSleep);
        app.setMinimumLogicUpdateInterval(maxLogicSleep);
        app.start();
    }

    /**
     * Initialize the game's components.
     *
     * @param container: the game container.
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container) throws SlickException {
        font = new TrueTypeFont(new Font("Sans", Font.PLAIN, 22), true);
        camera = new CameraManager(0, 0);
    }

    /**
     * Updates the content of the current displayed world.
     *
     * @param updateWorld: the world to be updated.
     */
    public void updateWorldContents(UpdateWorld updateWorld) {

        // update the actors list
        listActorManager = new CopyOnWriteArrayList<>();
        scoreBoardList = new LinkedList<>();
        for (Actor a : updateWorld.getActorList()) {
            if (a.getClass() == Moto.class) {
                scoreBoardList.add(a);
                listActorManager.add(new MotoManager((Moto) a, Color.decode("#2ecc71")));
                if (a.getId() == player.getId()) {
                    player.setActor(a);
                }
            }
            if (a.getClass() == Teleporter.class) {
                listActorManager.add(new TeleporterManager((Teleporter) a, Color.decode("#f1c40f")));
            }
            if (a instanceof Bonus) {
                listActorManager.add(new BonusManager((Bonus) a, Color.decode("#e74c3c")));
            }
        }

        // detect if map has changed
        if (map == null || !mapName.equals(updateWorld.getMapName())) {
            mapName = updateWorld.getMapName();
            mapHasChanged = true;
        }
    }

    /**
     * This is where the rendering of the game is done. Any render logic should be do here.
     *
     * @param container: the game container.
     * @param g: the game's graphics.
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        try {
            camera.onRender(g);

            Object o = serverHandler.getRecieved();
            if (o.getClass() == UpdateWorld.class) {
                // update world
                updateWorldContents((UpdateWorld)o);
            }

            // render current map
            if (map != null)
                this.map.render(0, 0);

            // Map switch if needed
            if (mapHasChanged) {
                // load new map
                String filePath = this.getClass().getClassLoader().getResource("resources/map/" + mapName).getPath();
                this.map = new TiledMap(filePath);
                mapHasChanged = false;
            }

            camera.setX(player.getActor().getLocation().getX() - container.getWidth() / 2);
            camera.setY(player.getActor().getLocation().getY() - container.getHeight() / 2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // render actors
        for (ActorManager am : listActorManager) {
            am.onRender(container, g, font);
        }

        renderScoreboard(container, g, scoreBoardList);
    }

    /**
     * This is where the logic of the game is done. As it's done in the server any logic should
     * be implemented in the server.
     *
     * @param container: the game container.
     * @param delta: the time elapsed between two updates
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }

    /**
     * Callback when a key is beeing press.
     *
     * @param key: the key that is beeing press.
     * @param c: the key's character code.
     */
    @Override
    public void keyPressed(int key, char c) {
        try {
            // delegate pressed key to players input handler
            inputManager.onInput(c, serverHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback when a key was released.
     *
     * @param key: the key that is beeing press.
     * @param c: the key's character code.
     */
    @Override
    public void keyReleased(int key, char c) {

    }

    /**
     * Render a scoreboard that displays the bests players by their number of kills.
     *
     * @param container
     * @param g
     * @param scoreBoardList
     */
    public void renderScoreboard(GameContainer container, Graphics g, LinkedList<Actor> scoreBoardList) {
        Collections.sort(scoreBoardList, (a, b) -> {
            if(a.getKills() >= b.getKills())
                return 1;
            else
                return  -1;
        });

        g.setColor(new Color(0, 0, 0, 80));
        g.fillRect(
                player.getActor().getLocation().getX()+container.getScreenWidth() / 2 - 200,
                player.getActor().getLocation().getY()-container.getScreenHeight() / 2 - 50,
                250,
                350
        );

        for(int i = 0; i < Math.min(10, scoreBoardList.size()); ++i) {
            Actor tmp = scoreBoardList.get(i);
            font.drawString(player.getActor().getLocation().getX() + container.getScreenWidth() / 2 - 180,
                    player.getActor().getLocation().getY() - container.getScreenHeight() / 2 + 20 + i * 50,
                    tmp.getKills() + " : " + tmp.getName(),
                    Color.white);
        }
    }
}
