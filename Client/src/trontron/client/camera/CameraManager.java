package trontron.client.camera;

import trontron.model.world.Direction;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author durza9390
 */
public class CameraManager {

    private float x;
    private float y;
    private final float speed;
    private float worldBoundariesX;
    private float worldBoundariesY;
    private final int width;
    private final int height;
    private Direction d;

    private final int space = 5;

    public CameraManager(float x, float y, float speed, float worldBoundariesX, float worldBoundariesY, int width, int height) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.worldBoundariesX = worldBoundariesX;
        this.worldBoundariesY = worldBoundariesY;
        this.width = width;
        this.height = height;
        this.d = Direction.noWhere;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void onRender(Graphics g) {
        g.translate(-x, -y);
    }

    public void printState() {
        System.out.println("[INFO] x" + x + " y " + y);
        System.out.println("[BOUNDARIES] x" + worldBoundariesX + " y " + worldBoundariesY);
    }

    public void setWorldBoundariesX(float worldBoundariesX) {
        this.worldBoundariesX = worldBoundariesX;
    }

    public void setWorldBoundariesY(float worldBoundariesY) {
        this.worldBoundariesY = worldBoundariesY;
    }
}
