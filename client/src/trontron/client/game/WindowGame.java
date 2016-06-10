package trontron.client.game;

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
import trontron.client.player.manager.Player;
import trontron.client.player.manager.InputManager;
import trontron.protocol.message.UpdateWorld;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.Font;
import java.util.List;
import java.util.ArrayList;


/**
 * @author durza9390
 */
public class WindowGame extends BasicGame {

    private AppGameContainer app;
    private TiledMap map;
    private CameraManager camera;
    private Player player;
    private List<ActorManager> listActorManager = new ArrayList();
    private InputManager inputManager;
    private ServerHandler serverHandler;
    private String mapName;
    private TrueTypeFont font;

    private boolean mapHasChanged = false;

    private static final String GAME_VERSION = "1.0";

    public WindowGame(String playerName, String hostname, int port) throws Exception {
        super("MCR projet - prototype");

        // create server handler
        serverHandler = new ServerHandler(hostname, port, this, playerName);

        // create player
        player = new Player("", 'w', 'd', 's', 'a');
        player.setId(serverHandler.getPlayerId());

        // todo : do better
        inputManager = new InputManager(player);

        // start listening for server messages
        serverHandler.start();

        int miniLogicSleep = 5;
        int maxLogicSleep = 20;
        AppGameContainer app = new AppGameContainer(this);

        //app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
        app.setDisplayMode(1920, 1080, false);
        app.setVSync(true);
        app.setTargetFrameRate(60);
        app.setMinimumLogicUpdateInterval(miniLogicSleep);
        app.setMinimumLogicUpdateInterval(maxLogicSleep);
        app.start();
    }

    public void setAppContainer(AppGameContainer app) {
        this.app = app;
    }

    public static String getVersion() {
        return GAME_VERSION;
    }

    @Override
    public void init(GameContainer container) throws SlickException {

        font = new TrueTypeFont(new Font("Times New Roman", Font.BOLD, 24), true);

        camera = new CameraManager(0, 0, 10,
                container.getWidth(),
                container.getHeight(),
                container.getWidth(),
                container.getHeight());
    }

    /**
     * Updates the content of the current displayed world
     * @param updateWorld
     */
    public void updateWorldContents(UpdateWorld updateWorld) {

        // update the actors list
        // todo : thread safe modification
        listActorManager = new ArrayList();
        for (Actor a : updateWorld.getActorList()) {
            if (a.getClass() == Moto.class) {
                listActorManager.add(new MotoManager((Moto) a, Color.green));
                if (a.getId() == player.getId()) {
                    player.setActor(a);
                }
            }
            if (a.getClass() == Teleporter.class) {
                listActorManager.add(new TeleporterManager((Teleporter) a, Color.yellow));
            }
            if (a instanceof Bonus) {
                listActorManager.add(new BonusManager((Bonus) a, Color.pink));
            }
        }

        // detect if map has changed
        if (map == null || !mapName.equals(updateWorld.getMapName())) {
            mapName = updateWorld.getMapName();
            mapHasChanged = true;
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        try {
            camera.onRender(g);

            // render current map
            if (map != null)
                this.map.render(0, 0);

            // Map switch if needed
            if (mapHasChanged) {
                // load new map
                String filePath = this.getClass().getClassLoader().getResource("resources/map/" + mapName).getPath();
                this.map = new TiledMap(filePath);
                mapHasChanged = false;

                camera.setWorldBoundariesX(map.getWidth() - container.getWidth());
                camera.setWorldBoundariesY(map.getHeight() - container.getHeight());
            }

            camera.setX(player.getActor().getLocation().getX() - container.getWidth() / 2);
            camera.setY(player.getActor().getLocation().getY() - container.getHeight() / 2);

        } catch (Exception e) {
            e.printStackTrace();
            this.app.destroy();
        }

        // render actors
        // todo : thread safe access
        for (ActorManager am : listActorManager) {
            am.onRender(container, g, font);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }

    @Override
    public void keyPressed(int key, char c) {
        try {
            // delegate pressed key to players input handler
            inputManager.onInput(c, serverHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyReleased(int key, char c) {

    }
}
