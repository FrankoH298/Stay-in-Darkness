package server.network;

import server.clients.Player;

public class Client {
    Player player = null;
    boolean logged = false;

    public Client(Player player, boolean logged) {
        this.player = player;
        this.logged = logged;
    }

    public Client(boolean logged) {
        this.logged = logged;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
