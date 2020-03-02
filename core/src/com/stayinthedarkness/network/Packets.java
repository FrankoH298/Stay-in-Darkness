package com.stayinthedarkness.network;

import com.stayinthedarkness.world.WorldPosition;

public class Packets {

    public static class Packet00Message {
        public String message;
    }

    public static class Packet01AddPlayer {
        public int id;
        public float x;
        public float y;
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
}
