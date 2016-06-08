package trontron.server.thread;

import trontron.protocol.message.PlayerIdentity;
import trontron.protocol.message.JoinGame;
import trontron.protocol.message.ChangeDirection;
import trontron.server.mediator.HyperMediator;
import trontron.protocol.Sender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Client handler
 */
public class ClientThread extends Thread {

    private HyperMediator mediator;
    private boolean running = true;
    private int playerId;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientThread(HyperMediator mediator, Socket socket) throws Exception {
        this.mediator = mediator;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (running) {
            try {
                // get client message
                Object message = in.readObject();

                // handle message
                if (message.getClass() == JoinGame.class) {
                    // add new player and store its id
                    playerId = mediator.addPlayer(new Sender(socket.getInetAddress().getCanonicalHostName(), 8001), (JoinGame)message);

                    // send players id to client
                    try {
                        out.reset();
                        out.writeObject(new PlayerIdentity(playerId));
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if (message.getClass() == ChangeDirection.class) {
                    // update player position
                    mediator.updatePlayer((ChangeDirection)message);
                }

            } catch (Exception e) {
                // remove player
                mediator.removePlayer(playerId);
                running = false;
            }
        }
    }
}
