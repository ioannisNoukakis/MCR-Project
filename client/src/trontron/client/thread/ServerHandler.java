package trontron.client.thread;

import trontron.client.game.WindowGame;
import trontron.protocol.message.JoinGame;
import trontron.protocol.message.PlayerIdentity;
import trontron.protocol.message.UpdateWorld;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by gdussier on 10.06.2016.
 */
public class ServerHandler extends Thread {

    /**
     * The socket used to communicate with the server
     */
    private Socket socket;

    /**
     * The stream used to send data to the client
     */
    private ObjectOutputStream out;

    /**
     * The stream used to receive data from the client
     */
    private ObjectInputStream in;

    /**
     * The main window to update
     */
    private WindowGame mainWindow;

    /**
     * The player id sended by the server
     */
    private int playerId;

    /**
     * Constructor
     * @param hostname The name of the server
     * @param port The port of the server
     * @param mainWindow The main window to update
     * @param playerName The name of the player to inscribe on the server
     * @throws Exception If the server is unreachable or does not respond
     */
    public ServerHandler(String hostname, int port, WindowGame mainWindow, String playerName) throws Exception {
        // connect to the server
        socket = new Socket(hostname, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        this.mainWindow = mainWindow;

        // subscribe player
        send(new JoinGame(playerName));

        // get player id from server
        Object message = in.readObject();
        if (message.getClass() == PlayerIdentity.class) {
            playerId = ((PlayerIdentity)message).getId();
        } else {
            throw new Exception("No player id received from server");
        }
    }

    /**
     * The receiving part of the handler
     */
    @Override
    public void run() {
        while (true) {
            try {
                // read message
                Object message = in.readObject();

                // handle message
                if (message.getClass() == UpdateWorld.class) {
                    // update world
                    mainWindow.updateWorldContents((UpdateWorld)message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Sends a message to the server
     * @param o The message object
     */
    public void send(Object o){
        try {
            out.reset();
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the id of the player
     * @return The id
     */
    public int getPlayerId() {
        return playerId;
    }
}
