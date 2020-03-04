package com.stayinthedarkness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stayinthedarkness.ventanas.GameScreen;
import com.stayinthedarkness.ventanas.MainMenu;

public class MainGame extends Game {

    // Virtual Screen size.
    public static final int V_WIDTH = 800;
    public static final int V_HEIGHT = 600;
    private SpriteBatch batch;

    @Override
    public void create() {

        batch = new SpriteBatch();

        // Seteamos la ventana de juego.
        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();

    }

    public SpriteBatch getSpriteBatch() {
        return this.batch;
    }
}
