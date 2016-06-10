package trontron.server.thread;

import trontron.protocol.message.PlayerIdentity;
import trontron.protocol.message.JoinGame;
import trontron.protocol.message.ChangeDirection;
import trontron.server.mediator.HyperMediator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client handler
 */
public class ClientHandler extends Thread {

    /**
     * The main mediator
     */
    private HyperMediator mediator;

    /**
     * Indicates if the thread should be running
     */
    private boolean running = true;

    /**
     * The id of the player managed by the thread
     */
    private int playerId;

    /**
     * The socket used to communicate with the client
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
     * Constructor
     * @param mediator The mediator for clients
     * @param socket The socket on which the client is connected
     * @throws IOException If the network streams are not available
     */
    public ClientHandler(HyperMediator mediator, Socket socket) throws IOException {
        this.mediator = mediator;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * The receiving part of the handler
     */
    @Override
    public void run() {
        while (running) {
            try {
                // get client message
                Object message = in.readObject();

                // handle message
                if (message.getClass() == JoinGame.class) {
                    // add new player and store its id
                    playerId = mediator.addPlayer(this, (JoinGame)message);

                    // send players id to client
                    send(new PlayerIdentity(playerId));
                }
                else if (message.getClass() == ChangeDirection.class) {
                    // update player position
                    mediator.updatePlayer((ChangeDirection)message);
                }
            }
            catch (Exception e) {
                // remove player
                mediator.removePlayer(playerId);
                running = false;
            }
        }

        // close socket
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an object to the client
     * @param o The object to send
     */
    public void send(Object o) {
        try {
            // send object
            out.reset();
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            // remove player
            mediator.removePlayer(playerId);
            running = false;
        }
    }
}
