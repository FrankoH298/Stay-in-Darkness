package server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class ServerNetworkListener extends Listener {
    private Server SiDServer;

    public ServerNetworkListener(Server SiDServer) {
        this.SiDServer = SiDServer;
    }

    @Override
    public void connected(Connection c) {
        Packets.Packet00Message p = new Packets.Packet00Message();
        p.message = "ID:" + c.getID() + " se conectó";
        SiDServer.sendToAllExceptTCP(c.getID(), p);
    }

    @Override
    public void disconnected(Connection c) {
        Packets.Packet00Message p = new Packets.Packet00Message();
        p.message = "ID:" + c.getID() + " se desconectó";
        SiDServer.sendToAllExceptTCP(c.getID(), p);
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof Packets.Packet00Message) {
            Packets.Packet00Message p = (Packets.Packet00Message) o;
            System.out.println("[CLIENT]: " + p.message);
        }
    }
}
