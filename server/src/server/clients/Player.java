package server.clients;

public class Player {
    public int id;
    public String name;
    public String password;
    public float x;
    public float y;
    public Gold gold;
    public Inventory inventory;
    public int heading;

    public Player(int id, float x, float y, int heading) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }
}