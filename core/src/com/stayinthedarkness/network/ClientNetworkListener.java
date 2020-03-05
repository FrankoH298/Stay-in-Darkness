package com.stayinthedarkness.network;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.stayinthedarkness.MainGame;

import javax.swing.*;

public class ClientNetworkListener extends Listener {
    private Client client;
    private MainGame game;

    public ClientNetworkListener(Client client, MainGame game) {
        this.client = client;
        this.game = game;
    }

    public void connected(Connection c) {
        System.out.println("Client connected");
    }

    public void disconnected(Connection c) {
        System.out.println("Client disconnected");
    }

    public void received(Connection c, Object o) {
        System.out.println(o);
        if (o instanceof Packets.Packet00Message) {
            Packets.Packet00Message p = (Packets.Packet00Message) o;
            System.out.println("[SERVER]: " + p.message);
            game.menu.gameScreen.getConsole().consoleTextAdd("[SERVER]: " + p.message);
        }else if (o instanceof Packets.Packet01AddPlayer) {
            Packets.Packet01AddPlayer p = (Packets.Packet01AddPlayer) o;
            game.menu.gameScreen.addPlayer(p.id, p.x, p.y,p.heading);
        }else if (o instanceof Packets.Packet02RemovePlayer) {
            Packets.Packet02RemovePlayer p = (Packets.Packet02RemovePlayer) o;
            game.menu.gameScreen.removePlayer(p.id);
        }else if (o instanceof Packets.Packet03UpdatePlayer) {
            Packets.Packet03UpdatePlayer p = (Packets.Packet03UpdatePlayer) o;
            game.menu.gameScreen.updatePlayer(p.id, p.x, p.y, p.heading);
        }else if (o instanceof Packets.Packet05LoginAnswer){
            Packets.Packet05LoginAnswer p = (Packets.Packet05LoginAnswer) o;
            game.setScreen(game.menu.gameScreen);
            game.menu.gameScreen.setMyID(p.id);
        }else if (o instanceof Packets.Packet06ErrorMessage){
            Packets.Packet06ErrorMessage p = (Packets.Packet06ErrorMessage) o;
            JOptionPane.showMessageDialog(null,p.msg,"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
