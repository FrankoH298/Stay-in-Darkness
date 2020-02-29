package com.stayinthedarkness.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.stayinthedarkness.world.worldPos;

public abstract class Entity {
    private int id;
    private worldPos position;
    private Batch batch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public worldPos getPosition() {
        return position;
    }

    public void setPosition(worldPos position) {
        this.position = position;
    }

    public Entity(Batch batch, float x, float y) {
        this.batch = batch;
        this.position = new worldPos(x, y);
    }


    public abstract static class Dynamic extends Entity {
        private Sprite sprite;
        private int heading;
        private float velocity;

        public Dynamic(Batch batch, float x, float y) {
            super(batch, x, y);
        }

        public void render() {
            //TODO: Metodos para renderizar las animaciones.
        }
    }

    public abstract static class Solid extends Entity {
        private Texture texture;

        public Solid(Batch batch, float x, float y, Texture texture) {
            super(batch, x, y);
            this.texture = texture;

        }

        public void render() {
            super.batch.draw(texture, super.getPosition().getX(), super.getPosition().getY());

        }

    }
}
