/*
 * HEIG-VD / MCR 
 * Ioannis Noukakis && Djomo Patrick Deslé
 * Laboratoire N°01
 * File : TronTronServer.java
 */
package trontronserver;

import Thread.ClientThread;
import java.net.ServerSocket;
import java.net.Socket;
import mediator.HyperMediator;

/**
 *
 * @author durza9390
 */
public class TronTronServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        HyperMediator mediator = new HyperMediator();
        try {
            ServerSocket serverSocker = new ServerSocket(Integer.parseInt(args[0]));
            System.out.println("Server started on " + Integer.parseInt(args[0]));
            
            while (true) {
                Socket socket = serverSocker.accept();
                new ClientThread(mediator, socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
