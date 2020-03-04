package server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import server.clients.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerNetworkListener extends Listener {
    private Server SiDServer;
    private Map<Integer, Client> userList;

    public ServerNetworkListener(Server SiDServer) {
        this.SiDServer = SiDServer;
        userList = new HashMap<Integer, Client>();
    }

    @Override
    public void connected(Connection c) {
        Packets.Packet00Message p = new Packets.Packet00Message();
        p.message = "ID:" + c.getID() + " se conectó";
        SiDServer.sendToAllExceptTCP(c.getID(), p);
        Packets.Packet01AddPlayer player = new Packets.Packet01AddPlayer();
        player.id = c.getID();
        player.x = 0;
        player.y = 0;
        player.heading = 0;
        SiDServer.sendToAllTCP(player);
        for (Client client : userList.values()) {
            Packets.Packet01AddPlayer playerNew = new Packets.Packet01AddPlayer();
            playerNew.id = client.player.id;
            playerNew.x = client.player.x;
            playerNew.y = client.player.y;
            playerNew.heading = client.player.heading;
            SiDServer.sendToTCP(c.getID(), playerNew);
        }
        userList.put(c.getID(), new Client(new Player(player.id, player.x, player.y, 0), true));
    }

    @Override
    public void disconnected(Connection c) {
        Packets.Packet00Message p = new Packets.Packet00Message();
        p.message = "ID:" + c.getID() + " se desconectó";
        SiDServer.sendToAllExceptTCP(c.getID(), p);
        Packets.Packet02RemovePlayer player = new Packets.Packet02RemovePlayer();
        player.id = c.getID();
        SiDServer.sendToAllTCP(player);
        userList.remove(c.getID());
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof Packets.Packet00Message) {
            Packets.Packet00Message p = (Packets.Packet00Message) o;
            System.out.println("[CLIENT]: " + p.message);
        }
        if (o instanceof Packets.Packet03UpdatePlayer) {
            Packets.Packet03UpdatePlayer p = (Packets.Packet03UpdatePlayer) o;
            userList.get(c.getID()).player.heading = p.heading;
            userList.get(c.getID()).player.x = p.x;
            userList.get(c.getID()).player.y = p.y;
            SiDServer.sendToAllExceptUDP(c.getID(), p);
        }
    }
}
