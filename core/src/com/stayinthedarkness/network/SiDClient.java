package com.stayinthedarkness.network;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.stayinthedarkness.MainGame;
import com.stayinthedarkness.screens.GameScreen;
import com.stayinthedarkness.screens.MainMenu;

import java.io.IOException;

public class SiDClient {

    // Private class
    private Game game;

    // Connection info
    private int TCPPort = 7666;
    private int UDPPort = 7666;
    private String IPAdress = "localhost";

    // Kryonet Client
    public Client client;
    private ClientNetworkListener clientListener;

    public SiDClient(MainGame game) {
        // Initialize client
        client = new Client();
        clientListener = new ClientNetworkListener(client, game);

        // Add listener
        registerPackets();
        client.addListener(clientListener);

        // Start client
        client.start();

        try {
            client.connect(5000, IPAdress, TCPPort, UDPPort);
        } catch (IOException e) {
            Gdx.app.exit();
            e.printStackTrace();
        }

    }

    private void registerPackets() {
        Kryo kryo = client.getKryo();
        kryo.register(Packets.Packet00Message.class);
        kryo.register(Packets.Packet01AddPlayer.class);
        kryo.register(Packets.Packet02RemovePlayer.class);
        kryo.register(Packets.Packet03UpdatePlayer.class);
    }
}
