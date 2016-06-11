package trontron.UDP;

import java.io.*;
import java.net.*;

public class Sender {
    
    private DatagramSocket dSock;
    private String hostName;
    private int desPort;

    public Sender(String hostName, int desPort) throws IOException {
        this.dSock = new DatagramSocket();
        this.hostName = hostName;
        this.desPort = desPort;
    }
    
    public void sendTo(Object o) throws IOException{
            InetAddress address = InetAddress.getByName(hostName);
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(50000);
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(o);
            os.flush();
            //retrieves byte array
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(
                    sendBuf, sendBuf.length, address, desPort);
            int byteCount = packet.getLength();
            dSock.send(packet);
            os.close();
    }
}
