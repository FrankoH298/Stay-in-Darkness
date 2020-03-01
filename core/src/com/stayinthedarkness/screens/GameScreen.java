package com.stayinthedarkness.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stayinthedarkness.MainGame;
import com.stayinthedarkness.entities.Solid.Tree;
import com.stayinthedarkness.world.TiledMapSiD;

public class GameScreen implements Screen {

    // VARIABLES
    private final MainGame game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Viewport viewPort;
    private final BitmapFont font;
    private final TiledMapSiD tiledMapSiD;
    private float velocity;

    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = game.getSpriteBatch();

        // Creamos una camara.
        this.camera = new OrthographicCamera();

        // FitViewport sirve para que al redimensionar el tamaño de la ventana se mantenga la escala.
        viewPort = new ScalingViewport(Scaling.none, MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        viewPort.apply();

        // Seteamos la posicion de la camara en la mitad de la ventana.
        camera.position.set(viewPort.getWorldWidth() / 2f, viewPort.getWorldHeight() / 2f, 0);
        font = new BitmapFont();
        ////////////////////////////////////////////////////////////////////////////////
        tiledMapSiD = new TiledMapSiD(batch);

    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        // Procesamos la entrada
        handleInput(delta);
        // Actualiza la camara.
        camera.update();
        // Le seteamos la camara al renderizado del mapa.
        tiledMapSiD.getRendererMap().setView(camera);
    }

    @Override
    public void render(float delta) { // Delta = Tiempo entre frame y frame.

        // Llamamos al metodo update enviandole el valor delta.
        update(delta);

        // Limpiamos la escena y le establecemos un fondo de color.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renderizamos el mapa fuera del batch ya que este lo vuelve a iniciar y terminar.
        tiledMapSiD.getRendererMap().render();

        // Al batch le va a setear la matriz de la camara y inicia el batch.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Mostramos los fps en la esquina izquierda-arriba de la ventana.
        drawText(font, batch, "FPS:" + Integer.toString(Gdx.graphics.getFramesPerSecond()), -(viewPort.getWorldWidth() / 2) + 10, (viewPort.getWorldHeight() / 2) - 10, 1f, 1f, 1f, 1f);

        // Fin del batch.
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        // Actualizamos el viewport
        viewPort.update(width, height);
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
        font.dispose();
        tiledMapSiD.dispose();
    }

    private void drawText(BitmapFont font, SpriteBatch batch, String text, float x, float y, float r, float g, float b, float a) {

        // Seteamos el color del texto a dibujar
        font.setColor(r, g, b, a);

        // Dibujamos la fuente y le seteamos la posicion en el centro de la camara + x o y
        font.draw(batch, text, camera.position.x + x, camera.position.y + y);
    }

    private void handleInput(float delta) {

        /* Multiplicamos la velocidad base y delta para que la velocidad no dependa de los frames. */
        velocity = (100f * delta);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -velocity);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(velocity, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-velocity, 0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
        }
    }
}
