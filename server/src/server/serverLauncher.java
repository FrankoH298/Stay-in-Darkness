package server;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class serverLauncher {

    //Connection Info
    private int TCPPort = 7666;
    private int UDPPort = 7666;

    // Kryonet Server
    private Server server;

    public serverLauncher() {
        server = new Server();
        try {
            server.bind(TCPPort, UDPPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] arg) {
        new serverLauncher();
    }
}
