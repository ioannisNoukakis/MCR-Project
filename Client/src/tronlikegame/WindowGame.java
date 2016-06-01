/*
 * Copyright (C) 2016 durza9390
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tronlikegame;

import ActorManager.ActorManager;
import ActorManager.MotoManager;
import Models.Actor.Actor;
import Models.Actor.Moto;
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
import org.newdawn.slick.Color;

/**
 *
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
    private static String host;
    
    private static final String GAME_VERSION = "1.0";
    
    public WindowGame() {
        super("MCR projet - prototype");
    }
    
    public void setAppContainer(AppGameContainer app) {
        this.app = app;
    }
    
    public static String getVersion() {
        return GAME_VERSION;
    }
    
    @Override
    public void init(GameContainer container) throws SlickException {
        this.map = new TiledMap("ressources/map/theGrid.tmx");
        camera = new CameraManager(0, 0, 10,
                map.getWidth() * map.getTileWidth() - container.getWidth(),
                map.getHeight() * map.getTileHeight() - container.getHeight(),
                container.getWidth(), container.getHeight());
        
        listActorManager = new LinkedList<>();
        
        try {
            syncServer = new SyncServer(host, 8000);
            syncServer.getOut().writeObject(new JoinGameFrame("inserer nom ici"));
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
    
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        camera.onRender(g);
        this.map.render(0, 0);
        
        try {
            Object o = syncServer.getInObject();
            
            if (o != null) {
                if (o.getClass() != GetWorldContents.class) {
                    throw new RuntimeException("Bad class recived. Aborting...");
                }
                
                listActorManager = new LinkedList<ActorManager>();
                GetWorldContents getWorldContents = (GetWorldContents) o;
                
                for (Actor a : getWorldContents.getActorList()) {
                    if (a.getClass() == Moto.class) {
                        listActorManager.add(new MotoManager((Moto) a, Color.green));
                        if (a.getId() == player.getId()) {
                            player.setActor(a);
                        }
                    }
                }
                camera.setX(player.getActor().getLocation().getX()-container.getWidth()/2);
                camera.setY(player.getActor().getLocation().getY()-container.getHeight()/2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        for (ActorManager am : listActorManager) {
            am.onRender(container, g);
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
    
    public static void main(String[] args) throws SlickException {
        try {
            host = args[0];
            int miniLogicSleep = 5;
            int maxLogicSleep = 20;
            WindowGame game = new WindowGame();
            AppGameContainer app = new AppGameContainer(game);
            game.setAppContainer(app);

            //app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
            app.setDisplayMode(800, 600, false);
            app.setVSync(true);
            app.setTargetFrameRate(60);
            app.setMinimumLogicUpdateInterval(miniLogicSleep);
            app.setMinimumLogicUpdateInterval(maxLogicSleep);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
