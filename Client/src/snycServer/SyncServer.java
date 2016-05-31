/*
 * Copyright (C) 2016 durza9390
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package snycServer;

import UDP.Reciever;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
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
        while(true)
        {
            inObject = UDPreciever.recvObjFrom();
        }
    }
}
