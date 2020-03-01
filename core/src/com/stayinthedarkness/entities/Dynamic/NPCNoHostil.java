package com.stayinthedarkness.entities.Dynamic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.stayinthedarkness.entities.Entity;
import com.stayinthedarkness.objects.Inventory;

public class NPCNoHostil extends Entity.Dynamic {
    private Inventory sellInventory;

    public NPCNoHostil(int id, float x, float y) {
        super(id, x, y);
    }
}
