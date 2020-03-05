package server.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import server.clients.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerNetworkListener extends Listener {
    private Server SiDServer;
    private Map<Integer, Client> clientList;
    private SQLConnection sqlConnection;

    public ServerNetworkListener(Server SiDServer) {
        this.SiDServer = SiDServer;
        clientList = new HashMap<Integer, Client>();
        sqlConnection = new SQLConnection();
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
        if (sqlConnection.checkPassword(name, password)) {

            // Le enviamos al usuario su id y le avisamos que esta logeado.
            Packets.Packet05LoginAnswer P05 = new Packets.Packet05LoginAnswer();
            P05.id = c.getID();
            SiDServer.sendToTCP(c.getID(), P05);

            // Avisamos a el servidor la id de la persona que entro.
            Packets.Packet00Message P00 = new Packets.Packet00Message();
            P00.message = "ID:" + c.getID() + " se conectó";
            SiDServer.sendToAllExceptTCP(c.getID(), P00);

            // Enviamos a los clientes el nuevo player.
            Player newPlayer = sqlConnection.loadUser(name);
            Packets.Packet01AddPlayer P01 = new Packets.Packet01AddPlayer();
            P01.id = c.getID();
            P01.x = newPlayer.x;
            P01.y = newPlayer.y;
            P01.heading = newPlayer.heading;
            SiDServer.sendToAllTCP(P01);

            // Le enviamos al cliente los players que estaban antes.
            for (Client client : clientList.values()) {
                if (client.logged) {
                    Packets.Packet01AddPlayer oldPlayer = new Packets.Packet01AddPlayer();
                    oldPlayer.id = client.player.id;
                    oldPlayer.x = client.player.x;
                    oldPlayer.y = client.player.y;
                    oldPlayer.heading = client.player.heading;
                    SiDServer.sendToTCP(c.getID(), oldPlayer);
                }
            }

            // Agregamos al cliente a la lista de clientes.
            clientList.put(c.getID(), new Client(newPlayer, true));
        } else {
            showMessageBox(c.getID(), "Usuario o contraseña incorrecta");
            c.close();
        }
    }

    private void showMessageBox(int id, String msg) {
        Packets.Packet06ErrorMessage pError = new Packets.Packet06ErrorMessage();
        pError.msg = msg;
        SiDServer.sendToTCP(id, pError);
    }
}
