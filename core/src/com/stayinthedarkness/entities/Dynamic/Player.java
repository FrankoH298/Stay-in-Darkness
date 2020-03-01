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


    public Player(int id, float x, float y) {
        super(id, x, y);
    }
}
