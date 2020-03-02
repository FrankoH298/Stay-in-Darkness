package server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import server.clients.Player;

import java.util.ArrayList;

public class ServerNetworkListener extends Listener {
    private Server SiDServer;
    private ArrayList<Player> players;

    public ServerNetworkListener(Server SiDServer) {
        this.SiDServer = SiDServer;
        this.players = new ArrayList<Player>();
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
        for (int a = 0; a < players.size(); a++) {
            Packets.Packet01AddPlayer playerNew = new Packets.Packet01AddPlayer();
            playerNew.id = players.get(a).id;
            playerNew.x = players.get(a).x;
            playerNew.y = players.get(a).y;
            playerNew.heading = players.get(a).heading;
            SiDServer.sendToTCP(c.getID(), playerNew);
        }
        players.add(new Player(player.id, player.x, player.y, 0));
    }

    @Override
    public void disconnected(Connection c) {
        Packets.Packet00Message p = new Packets.Packet00Message();
        p.message = "ID:" + c.getID() + " se desconectó";
        SiDServer.sendToAllExceptTCP(c.getID(), p);
        Packets.Packet02RemovePlayer player = new Packets.Packet02RemovePlayer();
        player.id = c.getID();
        SiDServer.sendToAllTCP(player);
        for (int a = 0; a < players.size(); a++) {
            if (players.get(a).id == c.getID()) {
                players.remove(a);
            }
        }
    }

    @Override
    public void received(Connection c, Object o) {
        if (o instanceof Packets.Packet00Message) {
            Packets.Packet00Message p = (Packets.Packet00Message) o;
            System.out.println("[CLIENT]: " + p.message);
        }
        if (o instanceof Packets.Packet03UpdatePlayer) {
            Packets.Packet03UpdatePlayer p = (Packets.Packet03UpdatePlayer) o;
            for (int a = 0; a < players.size(); a++) {
                if (players.get(a).id == c.getID()) {
                    players.get(a).heading = p.heading;
                    players.get(a).x = p.x;
                    players.get(a).y = p.y;
                }
            }
            SiDServer.sendToAllExceptUDP(c.getID(),p);
        }
    }
}
