package com.stayinthedarkness.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientNetworkListener extends Listener {
    private Client client;

    public ClientNetworkListener(Client client) {
        this.client = client;
    }

    public void connected(Connection c) {
        System.out.println("Client connected");
    }

    public void disconnected(Connection c) {
        System.out.println("Client disconnected");
    }

    public void received(Connection c, Object o) {
        if (o instanceof Packets.Packet00Message) {
            Packets.Packet00Message p = (Packets.Packet00Message) o;
            System.out.println("[SERVER]:" + p.message);
        }
    }
}
