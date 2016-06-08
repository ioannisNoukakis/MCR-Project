package trontron.server;

import trontron.server.mediator.HyperMediator;
import trontron.server.thread.ClientThread;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author durza9390
 */
public class ServerLauncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            HyperMediator mediator = new HyperMediator();

            int port = 8000;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
            ServerSocket serverSocker = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocker.accept();
                new ClientThread(mediator, socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
