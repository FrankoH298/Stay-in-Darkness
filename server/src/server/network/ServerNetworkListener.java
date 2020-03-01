package server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import server.serverLauncher;

public class ServerNetworkListener extends Listener {
    serverLauncher SiDServer;

    public ServerNetworkListener(serverLauncher SiDServer) {
        this.SiDServer = SiDServer;
    }

    @Override
    public void connected(Connection c) {
        System.out.println("Alguien se conecto");
    }

    @Override
    public void disconnected(Connection c) {
        System.out.println("Alguien se desconecto");
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof Packets.Packet00Message) {
            Packets.Packet00Message p = (Packets.Packet00Message) o;
            System.out.println("[CLIENT]: " + p.message);

        }
    }
}
