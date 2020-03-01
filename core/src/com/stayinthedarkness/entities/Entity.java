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
        this.position = new WorldPosition(x, y);
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
        position.setX(position.getX() + x);
        position.setY(position.getY() + y);
    }


    public abstract static class Dynamic extends Entity {
        private Array<Animation> animations;
        private int heading;
        private float velocity;
        private float stateTimer;
        private TextureRegion texture;

        public Dynamic(int id, float x, float y) {
            super(id, x, y);
            stateTimer = 0;
            Texture texture = new Texture(Gdx.files.internal("graphics/1.png"));
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
            //TODO: Metodos para renderizar las animaciones.
            batch.draw(texture, super.getPosition().getX(), super.getPosition().getY());


        }
    }

    public abstract static class Solid extends Entity {
        private Texture texture;

        public Solid(int id, float x, float y, Texture texture) {
            super(id, x, y);
            this.texture = texture;
        }

        public void render(Batch batch) {
            batch.draw(texture, super.getPosition().getX(), super.getPosition().getY());

        }

    }
}
