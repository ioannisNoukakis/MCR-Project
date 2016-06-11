package trontron.client.camera;

import trontron.model.world.Direction;
import org.newdawn.slick.Graphics;

/**
 * This is the camera manager. It follows the player's actor.
 */
public class CameraManager {

    private float x;
    private float y;

    public CameraManager(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Set the X position for this camera.
     *
     * @param x: the x position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Set the Y position for this camera.
     *
     * @param y: the y position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Applies the translation to show on the GUI a defined aera.
     *
     * @param g: the graphic where the translation will be applied.
     */
    public void onRender(Graphics g) {
        g.translate(-x, -y);
    }

    /**
     * [DEBUG] Prints the x and y coordinates of this camera.
     */
    public void printState() {
        System.out.println("[INFO] x" + x + " y " + y);
    }
}
