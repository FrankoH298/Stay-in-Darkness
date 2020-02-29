package com.stayinthedarkness.entities.dynamic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.stayinthedarkness.entities.Entity;
import com.stayinthedarkness.objects.Gold;
import com.stayinthedarkness.objects.Item;

public class NPCHostil extends Entity.Dynamic {
    private int health;
    private Item drop;
    private Gold goldDrop;

    public NPCHostil(Batch batch,float x,float y) {
        super(batch,x,y);
    }
}
