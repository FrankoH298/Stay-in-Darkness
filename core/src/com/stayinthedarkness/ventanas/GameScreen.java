package com.stayinthedarkness.ventanas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    private BitmapFont font;
    private FitViewport viewport;

    public GameScreen(StayintheDarkness game) {
        this.game = game;
        this.batch = game.getSpriteBatch();
        int vpWidth = Gdx.graphics.getWidth(),
                vpHeight = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera(vpWidth, vpHeight); // Creamos una camara con el tamaño de la ventana.
        textura = new Texture(Gdx.files.internal("default.png"));
        camera.position.set(vpWidth / 2, vpHeight / 2, 0);
        font = new BitmapFont();
        viewport = new FitViewport(vpWidth, vpHeight, camera); // FitViewport sirve para que al redimensionar el tamaño de la ventana se mantenga la escala.
        viewport.apply();
    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        velocity = (100f * delta); // Multiplicamos la velocidad base y delta para que la velocidad no dependa de los frames.
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
    public void render(float delta) { // Delta = Tiempo entre frame y frame.
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f); // Limpiamos la escena y le establecemos un fondo de color.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta); // Llamamos al metodo update mandandole delta.
        batch.setProjectionMatrix(camera.combined); // Al batch le va a setear la matriz de la camara.
        batch.begin(); // Inicio del batch, a partir de aqui van todos los draw que utilicen batch.
        batch.draw(textura, 0, 0);
        batch.end(); // Fin del batch.

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height); // Actualizamos el viewport
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

    private void drawText(BitmapFont font, SpriteBatch batch, String text, float x, float y, float r, float g, float b, float a) {
        font.setColor(r, g, b, a); // Seteamos el color del texto a dibujar
        font.draw(batch, text, camera.position.x + x, camera.position.y + y); // Dibujamos la fuente y le seteamos la posicion en el centro de la camara + x o y
    }
}
