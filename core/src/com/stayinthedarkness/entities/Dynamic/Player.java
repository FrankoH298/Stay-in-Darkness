package com.stayinthedarkness.entities.Dynamic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.stayinthedarkness.entities.Entity;
import com.stayinthedarkness.objects.Gold;
import com.stayinthedarkness.objects.Inventory;
import com.stayinthedarkness.world.WorldPosition;

public class Player extends Entity.Dynamic {
    private Inventory inventory;
    private int health;
    private Gold gold;


    public Player(int id, int grhNumber, float x, float y) {
        super(id, grhNumber, x, y);
    }

    public float getCenterPositionW(float delta) {
        return (super.getFrame(delta).getRegionWidth() / 2);
    }

    public float getCenterPositionH(float delta) {
        return (super.getFrame(delta).getRegionHeight() / 2);
    }
}
