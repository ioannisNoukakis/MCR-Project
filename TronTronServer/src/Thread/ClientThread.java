package Thread;

import UDP.Sender;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mediator.HyperMediator;

/**
 * @author durza9390
 */
public class ClientThread extends Thread {
    private HyperMediator hyperMediator;
    private Socket socket;
    private boolean running = true;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ClientThread(HyperMediator hyperMediator, Socket socket) throws Exception {
        this.hyperMediator = hyperMediator;
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (running) {
            try {
                hyperMediator.parseClientInput(new Sender(socket.getInetAddress().getCanonicalHostName(), 8001), out, in.readObject());
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        }
    }
}
