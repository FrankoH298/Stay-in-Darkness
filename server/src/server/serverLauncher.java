package server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import server.network.Packets;
import server.network.ServerNetworkListener;

import java.io.IOException;

public class serverLauncher {

    //Connection Info
    private int TCPPort = 7666;
    private int UDPPort = 7666;

    // Kryonet Server
    private Server server;
    private ServerNetworkListener serverListener;

    public serverLauncher() {
        server = new Server();
        serverListener = new ServerNetworkListener(server);
        server.addListener(serverListener);


        try {
            server.bind(TCPPort, UDPPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        registerPackets();

        // Start server
        server.start();
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        kryo.register(Packets.Packet00Message.class);
        kryo.register(Packets.Packet01AddPlayer.class);
    }

    public static void main(String[] arg) {
        new serverLauncher();
    }
}
