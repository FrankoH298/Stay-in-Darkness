package com.stayinthedarkness.entities.dynamic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.stayinthedarkness.entities.Entity;
import com.stayinthedarkness.objects.Gold;
import com.stayinthedarkness.objects.Inventory;

public class Player extends Entity.Dynamic {
    private Inventory inventory;
    private int health;
    private Gold gold;

    public Player(Batch batch, float x, float y) {
        super(batch, x, y);
    }
}
