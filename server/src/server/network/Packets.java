package server.network;

public class Packets {

    public static class Packet00Message {
        public String message;
    }

    public static class Packet01AddPlayer {
        public int id;
        public float x;
        public float y;
        public int heading;
    }

    public static class Packet02RemovePlayer {
        public int id;
    }

    public static class Packet03UpdatePlayer {
        public int id;
        public float x;
        public float y;
        public int heading;
    }

    public static class Packet04LoginRequest {
        public String name;
        public String password;
    }

    public static class Packet05LoginAnswer {
        public int id;
    }

    public static class Packet06ErrorMessage {
        public String msg;
    }
}
