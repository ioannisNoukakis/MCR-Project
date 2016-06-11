package trontron.client.player;

import trontron.model.actor.Actor;
import trontron.model.world.Direction;

/**$
 * Contains all usefull data for a player.
 */
public class Player {
    private Actor actor;
    private char rightKey;
    private char leftKey;
    private char upKey;
    private char downKey;
    private int id;

    public Player(char upKey, char rightKey, char downbKey, char leftKey) {
        this.rightKey = rightKey;
        this.leftKey = leftKey;
        this.upKey = upKey;
        this.downKey = downbKey;
    }

    /**
     * Set an id for this player.
     *
     * @param id: the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns this player's actor.
     *
     * @return Actor
     */
    public Actor getActor() {
        return actor;
    }

    /**
     * Return this player's id.
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Set this player's actor.
     *
     * @param actor: the actor.
     */
    public void setActor(Actor actor) {
        this.actor = actor;
    }

    /**
     * Returns this player's actor direction.
     *
     * @return Direction
     */
    public Direction getActorDirection() {
        return actor.getDirection();
    }

    /**
     * Set this player's actor direction.
     *
     * @return Direction
     */
    public void setActorDirection(Direction d) {
        actor.setDirection(d);
    }

    /**
     * Return true if c was the right key binding for this player.
     *
     * @param c: the caracter to be checked.
     *
     * @return boolean
     */
    public boolean isItRightKey(char c) {
        return c == rightKey;
    }

    /**
     * Return true if c was the left key binding for this player.
     *
     * @param c: the caracter to be checked.
     *
     * @return boolean
     */
    public boolean isItLeftKey(char c) {
        return c == leftKey;
    }

    /**
     * Return true if c was the up key binding for this player.
     *
     * @param c: the caracter to be checked.
     *
     * @return boolean
     */
    public boolean isItUpKey(char c) {
        return c == upKey;
    }

    /**
     * Return true if c was the down key binding for this player.
     *
     * @param c: the caracter to be checked.
     *
     * @return boolean
     */
    public boolean isItDownKey(char c) {
        return c == downKey;
    }
}
