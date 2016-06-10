package trontron.server;

import trontron.protocol.Common;
import trontron.server.mediator.HyperMediator;
import trontron.server.thread.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * The launcher for the server
 */
public class ServerLauncher {

    /**
     * Main program
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // create the main mediator
            HyperMediator mediator = new HyperMediator();

            // use the default port or get it from the program arguments
            int port = Common.DEFAULT_SERVER_PORT;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }

            // create server socket
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            // accept clients
            while (true) {
                Socket socket = serverSocket.accept();

                // start a new thread for each client
                new ClientHandler(mediator, socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
