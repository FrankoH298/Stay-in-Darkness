package com.stayinthedarkness.entities.Dynamic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.stayinthedarkness.entities.Entity;
import com.stayinthedarkness.objects.Gold;
import com.stayinthedarkness.objects.Item;

public class NPCHostil extends Entity.Dynamic {
    private int health;
    private Item drop;
    private Gold goldDrop;

    public NPCHostil(int id, int grhNumber, float x, float y) {
        super(id,grhNumber, x, y);
    }

}
