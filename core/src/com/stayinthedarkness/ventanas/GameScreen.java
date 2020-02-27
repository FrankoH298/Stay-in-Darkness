package com.stayinthedarkness.ventanas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.stayinthedarkness.StayintheDarkness;

/**
 *
 * @author franc
 */
public class GameScreen implements Screen {

    private StayintheDarkness game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture textura;
    private float velocity;

    public GameScreen(StayintheDarkness game) {
        this.game = game;
        this.batch = game.getSpriteBatch();
        int vpWidth = Gdx.graphics.getWidth(),
                vpHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(vpWidth, vpHeight); // Creamos una camara con el tamaño de la ventana.
        textura = new Texture(Gdx.files.internal("default.png"));
        camera.position.set(vpWidth / 2, vpHeight / 2, 0);
    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        velocity = (1f);
        //System.out.println(velocity);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(velocity, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-velocity, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.COMMA)) {
            if (camera.zoom > 0.2f) {
                camera.zoom -= 0.01f;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.PERIOD)) {
            if (camera.zoom < 2f) {
            camera.zoom += 0.01f;
            }
        }
        camera.update(); // Actualiza la camara.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f); // Limpiamos la escena y le establecemos un fondo de color.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined); // Al batch le va a setear la matriz de la camara.
        update(delta);
        batch.begin();
        batch.draw(textura, 0, 0);
        batch.end();
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
