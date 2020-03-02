package com.stayinthedarkness.entities.Dynamic;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.stayinthedarkness.entities.Entity;
import com.stayinthedarkness.objects.Inventory;

public class NPCNoHostil extends Entity.Dynamic {
    private Inventory sellInventory;

    public NPCNoHostil(int id, float x, float y, Array<Animation> animations) {
        super(id, x, y, animations);
    }
}
