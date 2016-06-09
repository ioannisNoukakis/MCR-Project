package trontron.protocol;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Reciever {

    private DatagramSocket dSock;

    public Reciever(int port) throws Exception {
        this.dSock = new DatagramSocket(port);
    }
    
    public Object recvObjFrom() {
        int byteCount = 0;
        try {
            byte[] recvBuf = new byte[50000];
            DatagramPacket packet = new DatagramPacket(recvBuf,
                    recvBuf.length);
            dSock.receive(packet);
            byteCount = packet.getLength();
            ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream));
            Object o = is.readObject();
            is.close();
            return(o);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return (null);
    }
}
