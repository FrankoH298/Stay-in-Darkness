package com.stayinthedarkness.entities.Solid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.stayinthedarkness.entities.Entity;

public class Tree extends Entity.Solid {

    public Tree(int id, float x, float y, Texture texture) {
        super(id, x, y, texture);
    }

    @Override
    public void render(Batch batch) {
        super.render(batch);
    }
}
