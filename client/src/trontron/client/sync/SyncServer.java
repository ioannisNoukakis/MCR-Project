package trontron.client.sync;

import trontron.protocol.Reciever;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author durza9390
 */
public class SyncServer extends Thread {

    private Socket socket;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private Object inObject;
    Reciever UDPreciever;

    public SyncServer(String hostname, int port) throws Exception {
        this.socket = new Socket(hostname, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        UDPreciever = new Reciever(8001);
    }

    public synchronized Object getInObject() {
        return inObject;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    @Override
    public void run() {
        while (true) {
            inObject = UDPreciever.recvObjFrom();
        }
    }
}
