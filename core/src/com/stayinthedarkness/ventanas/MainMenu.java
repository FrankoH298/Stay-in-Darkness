package com.stayinthedarkness.ventanas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.stayinthedarkness.StayintheDarkness;

/**
 *
 * @author franc
 */
public class MainMenu implements Screen {

    private StayintheDarkness game;

    public MainMenu(StayintheDarkness game) {
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getSpriteBatch().begin();
        game.getSpriteBatch().end();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }

}
