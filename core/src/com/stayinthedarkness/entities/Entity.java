package com.stayinthedarkness.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.stayinthedarkness.world.WorldPosition;

import javax.swing.*;

public abstract class Entity {
    private int id;
    private WorldPosition position;

    public Entity(int id, float x, float y) {
        this.id = id;
        this.position = new WorldPosition();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public WorldPosition getPosition() {
        return position;
    }

    public void setPosition(WorldPosition position) {
        this.position = position;
    }

    public void translate(float x, float y) {
        position.x += x;
        position.y += y;
    }


    public abstract static class Dynamic extends Entity {
        private Array<Animation> animations;
        private int heading = 0;
        private float velocity = 100f;
        private float stateTimer = 0f;
        private TextureRegion texture;

        public Dynamic(int id, int grhNumber, float x, float y) {
            super(id, x, y);
            Texture texture = new Texture(Gdx.files.internal("graphics/" + grhNumber + ".png"));
            animations = new Array<Animation>();
            Array<TextureRegion> frames = new Array<TextureRegion>();
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 6; b++) {
                    frames.add(new TextureRegion(texture, 27 * b, 47 * a, 27, 47));
                }
                animations.add(new Animation(0.1f, frames));
                frames.clear();
            }

        }

        public TextureRegion getFrame(float delta) {
            return (TextureRegion) animations.get(getHeading()).getKeyFrame(stateTimer, true);
        }

        public void update(float delta) {
            texture = getFrame(delta);
        }

        public float getStateTimer() {
            return stateTimer;
        }

        public void setStateTimer(float stateTimer) {
            this.stateTimer = stateTimer;
        }

        public void updateStateTimer(float delta) {
            this.stateTimer += delta;
        }

        public int getHeading() {
            return heading;
        }

        public void setHeading(int heading) {
            this.heading = heading;
        }

        public float getVelocity() {
            return velocity;
        }

        public void setVelocity(float velocity) {
            this.velocity = velocity;
        }

        public void render(Batch batch) {
            // Dibuja la keyFrame en la posicion de la entidad.
            batch.draw(texture, super.getPosition().x, super.getPosition().y);
        }
    }

    public abstract static class Solid extends Entity {
        private Texture texture;

        public Solid(int id, float x, float y, Texture texture) {
            super(id, x, y);
            this.texture = texture;
        }

        public void render(Batch batch) {
            // Dibuja la textura en la posicion de la entidad.
            batch.draw(texture, super.getPosition().x, super.getPosition().y);

        }

    }
}
