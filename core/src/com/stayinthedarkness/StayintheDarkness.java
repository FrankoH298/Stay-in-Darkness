package com.stayinthedarkness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stayinthedarkness.ventanas.MainMenu;

public class StayintheDarkness extends Game {

    private SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MainMenu(this));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
    public SpriteBatch getSpriteBatch(){
        return this.batch;
    }
}
