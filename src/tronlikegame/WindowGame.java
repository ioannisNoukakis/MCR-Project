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

import Actor.Actor;
import Actor.Moto;
import camera.CameraManager;
import java.util.LinkedList;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import playerManager.Player;
import playerManager.inputMediator;
import world.Point2D;

/**
 *
 * @author durza9390
 */
public class WindowGame extends BasicGame {
    
    private AppGameContainer app;
    private TiledMap map;
    private CameraManager camera;
    private LinkedList<Player> listPlayer;
    private LinkedList<Actor> listActor;
    private inputMediator inputMediator;

    private static final String GAME_VERSION = "1.0";   

    
    public WindowGame() {
        super("MCR projet - prototype");
    }
    
    public void setAppContainer(AppGameContainer app)
    {
        this.app = app;
    }
    
    public static String getVersion() {
        return GAME_VERSION;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.map = new TiledMap("D:\\dev\\MCR-Project\\ressources\\map\\theGrid.tmx");
        camera = new CameraManager(0, 0, 10,
                map.getWidth() * map.getTileWidth() - container.getWidth(),
                map.getHeight() * map.getTileHeight() - container.getHeight(),
                container.getWidth(), container.getHeight());
        
        listPlayer = new LinkedList<>();
        listActor = new LinkedList<>();
        
        // mise en place des acteurs
        listActor.add(new Moto("mustang 1", new Point2D(50, 50), (float)0.5, 100, Color.red, 30));
        listActor.add(new Moto("narval 500", new Point2D(100, 100), (float)0.5, 100, Color.blue, 30));
        
        //mise en place des joueurs
        listPlayer.add(new Player(listActor.get(0), "Player 1", 'w', 'd', 's', 'a'));
        listPlayer.add(new Player(listActor.get(1), "Player 2", 'i', 'l', 'k', 'j'));
        inputMediator = new inputMediator(listPlayer);
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        camera.onRender(g);
        this.map.render(0, 0);
        
        for(Actor a : listActor)
        {
            a.onRender(container, g);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        camera.onUserInput(container);
        camera.onUpdate(delta);
        
        for(Actor a : listActor)
        {
            a.onUpdate(container, delta);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
       inputMediator.onInput(c);
    }

    @Override
    public void keyReleased(int key, char c) {
        
    }
    
    public static void main(String[] args) throws SlickException {
        try {
            int miniLogicSleep = 5;
            int maxLogicSleep = 20;
            WindowGame game = new WindowGame();
            AppGameContainer app = new AppGameContainer(game);
            game.setAppContainer(app);

            app.setDisplayMode(app.getScreenWidth(), app.getScreenHeight(), true);
            //app.setDisplayMode(800, 600, false);
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
