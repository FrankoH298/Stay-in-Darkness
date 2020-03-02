package server.network;

public class Packets {

    public static class Packet00Message {
        public String message;
    }

    public static class Packet01AddPlayer {
        public int id;
        public float x;
        public float y;
    }

}
