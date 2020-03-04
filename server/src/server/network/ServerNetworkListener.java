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
    private Map<Integer, Client> clientList;

    public ServerNetworkListener(Server SiDServer) {
        this.SiDServer = SiDServer;
        clientList = new HashMap<Integer, Client>();
    }

    @Override
    public void connected(Connection c) {
        clientList.put(c.getID(), new Client(false));
    }

    @Override
    public void disconnected(Connection c) {
        if (clientList.get(c.getID()).logged) {
            Packets.Packet00Message p = new Packets.Packet00Message();
            p.message = "ID:" + c.getID() + " se desconectó";
            SiDServer.sendToAllExceptTCP(c.getID(), p);
            Packets.Packet02RemovePlayer player = new Packets.Packet02RemovePlayer();
            player.id = c.getID();
            SiDServer.sendToAllTCP(player);
            clientList.remove(c.getID());
        }
    }

    @Override
    public void received(Connection c, Object o) {
        if (clientList.get(c.getID()).logged == false) {
            if (o instanceof Packets.Packet04LoginRequest) {
                Packets.Packet04LoginRequest p04 = (Packets.Packet04LoginRequest) o;
                connectUser(c, p04.name, p04.password);
            } else {
                c.close();
            }
        } else {
            if (o instanceof Packets.Packet00Message) {
                Packets.Packet00Message p = (Packets.Packet00Message) o;
                System.out.println("[CLIENT]: " + p.message);
            } else if (o instanceof Packets.Packet03UpdatePlayer) {
                Packets.Packet03UpdatePlayer p = (Packets.Packet03UpdatePlayer) o;
                clientList.get(c.getID()).player.heading = p.heading;
                clientList.get(c.getID()).player.x = p.x;
                clientList.get(c.getID()).player.y = p.y;
                SiDServer.sendToAllExceptUDP(c.getID(), p);
            }
        }

    }

    private void connectUser(Connection c, String name, String password) {

        // Corroboramos si su informacion es correcta.
        if (name.equals("Franco") && password.equals("asd")) {

            // Le enviamos al usuario su id y le avisamos que esta logeado.
            Packets.Packet05LoginAnswer P05 = new Packets.Packet05LoginAnswer();
            P05.logged = true;
            P05.yourID = c.getID();
            SiDServer.sendToTCP(c.getID(), P05);

            // Avisamos a el servidor la id de la persona que entro.
            Packets.Packet00Message P00 = new Packets.Packet00Message();
            P00.message = "ID:" + c.getID() + " se conectó";
            SiDServer.sendToAllExceptTCP(c.getID(), P00);

            // Enviamos a los clientes el nuevo player.
            Packets.Packet01AddPlayer P01 = new Packets.Packet01AddPlayer();
            P01.id = c.getID();
            P01.x = 0;
            P01.y = 0;
            P01.heading = 0;
            SiDServer.sendToAllTCP(P01);

            // Le enviamos al cliente los players que estaban antes.
            for (Client client : clientList.values()) {
                if (client.logged) {
                    Packets.Packet01AddPlayer playerNew = new Packets.Packet01AddPlayer();
                    playerNew.id = client.player.id;
                    playerNew.x = client.player.x;
                    playerNew.y = client.player.y;
                    playerNew.heading = client.player.heading;
                    SiDServer.sendToTCP(c.getID(), playerNew);
                }
            }

            // Agregamos al cliente a la lista de clientes.
            clientList.put(c.getID(), new Client(new Player(P01.id, P01.x, P01.y, 0), true));
        } else {
            c.close();
            // TODO: Crear paquete errorMessage que avise el tipo de error al cliente.
        }
    }
}
