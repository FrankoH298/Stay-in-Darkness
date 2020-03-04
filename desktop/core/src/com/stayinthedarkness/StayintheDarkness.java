package com.stayinthedarkness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stayinthedarkness.screens.MainMenu;

public class StayintheDarkness extends Game {

    private SpriteBatch batch;
    
    @Override
    public void create() {
        batch = new SpriteBatch(); // Inicializacion del SpriteBatch
        this.setScreen(new MainMenu(this)); // A la ventana se le setea el menu.
    }
    

    @Override
    public void dispose() {
        batch.dispose(); // Limpia el spritebatch
    }
    public SpriteBatch getSpriteBatch(){
        return this.batch;
    }
}
