package server.clients;

public class Player {
    public int id;
    public int indexPJ;
    public float x;
    public float y;
    public int heading;
    public String name;
    public String password;

    public Gold gold;
    public Inventory inventory;


    public Player(int id, int indexPJ, float x, float y, int heading) {
        this.id = id;
        this.indexPJ = indexPJ;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Player(int indexPJ, float x, float y, int heading) {
        this.indexPJ = indexPJ;
        this.x = x;
        this.y = y;
        this.heading = heading;
    }
}