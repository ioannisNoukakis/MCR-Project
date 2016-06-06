package tronlikegame;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import ActorManager.TeleporterManager;
import Models.Actor.Actor;
import Models.Actor.Moto;
import Models.Actor.Teleporter;
import Models.Protocol.Connection.GetIdentity;
import Models.Protocol.Connection.JoinGameFrame;
import camera.CameraManager;

import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import playerManager.Player;
import playerManager.InputManager;
import snycServer.SyncServer;
import Models.Protocol.Sync.GetWorldContents;
import java.awt.Font;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

/**
 * @author durza9390
 */
public class WindowGame extends BasicGame {

    private AppGameContainer app;
    private TiledMap map;
    private CameraManager camera;
    private Player player;
    private LinkedList<ActorManager> listActorManager;
    private InputManager inputManager;
    private SyncServer syncServer;
    private String host;
    private int port; 
    private String playerName;
    private String mapName;
    private TrueTypeFont font;

    private static final String GAME_VERSION = "1.0";

    public WindowGame(String playerName, String hostname, int port) throws Exception {
        super("MCR projet - prototype");
        this.playerName = playerName;
        this.host = hostname;
        this.port = port;
        syncServer = new SyncServer(host, 8000);
        
        int miniLogicSleep = 5;
        int maxLogicSleep = 20;
        AppGameContainer app = new AppGameContainer(this);

        //app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
        app.setDisplayMode(800, 600, false);
        app.setVSync(false);
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
        listActorManager = new LinkedList<>();
        mapName = "";
        camera = new CameraManager(0, 0, 10,
                container.getWidth(),
                container.getHeight(),
                container.getWidth(),
                container.getHeight());

        try {
            syncServer.getOut().writeObject(new JoinGameFrame(playerName));
            syncServer.start();

            player = (new Player("", 'w', 'd', 's', 'a'));

            Object o = syncServer.getIn().readObject();
            if (o.getClass() == GetIdentity.class) {
                GetIdentity identity = (GetIdentity) o;
                player.setId(identity.getId());
            } else {
                throw new RuntimeException("Bad class recived. Aborting...");
            }

            inputManager = new InputManager(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        try {
            Object o = syncServer.getInObject();
            camera.onRender(g);

            if (map != null)
                this.map.render(0, 0);
            

            if (o != null) {
                if (o.getClass() == GetWorldContents.class) {

                    listActorManager = new LinkedList<>();
                    GetWorldContents getWorldContents = (GetWorldContents) o;

                    for (Actor a : getWorldContents.getActorList()) {
                        //System.out.println(a.getLocation().getX() + " : " + a.getLocation().getY());
                        if (a.getClass() == Moto.class) {
                            listActorManager.add(new MotoManager((Moto) a, Color.green));
                            if (a.getId() == player.getId()) {
                                player.setActor(a);
                            }
                        }
                        if(a.getClass() == Teleporter.class)
                        {
                            listActorManager.add(new TeleporterManager((Teleporter) a, Color.yellow));
                        }
                    }

                    //changement de map si nécessaire
                    if (!mapName.equals(getWorldContents.getMapName())) {
                        this.map = new TiledMap("ressources/map/" + getWorldContents.getMapName());
                        camera.setWorldBoundariesX(map.getWidth() - container.getWidth());
                        camera.setWorldBoundariesY(map.getHeight() - container.getHeight());
                        mapName = getWorldContents.getMapName();
                    }

                    camera.setX(player.getActor().getLocation().getX() - container.getWidth() / 2);
                    camera.setY(player.getActor().getLocation().getY() - container.getHeight() / 2);
                    
                    System.out.println(player.getActor().getLocation().getX() + " : " + player.getActor().getLocation().getY());
                } else {
                    throw new RuntimeException("Bad class recived. Aborting...");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.app.destroy();
        }

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
            inputManager.onInput(c, syncServer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyReleased(int key, char c) {

    }
}
