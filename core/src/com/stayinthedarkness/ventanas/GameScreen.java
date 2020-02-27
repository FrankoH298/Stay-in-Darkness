package com.stayinthedarkness.ventanas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.stayinthedarkness.StayintheDarkness;

public class GameScreen implements Screen {

    // VARIABLES
    private StayintheDarkness game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture textura;
    private float velocity;
    private BitmapFont font;
    private FitViewport viewport;
    private float vpWidth = Gdx.graphics.getWidth();
    private float vpHeight = Gdx.graphics.getHeight();
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer rendererMap;

    public GameScreen(StayintheDarkness game) {
        this.game = game;
        this.batch = game.getSpriteBatch();

        // Creamos una camara con el tamaño de la ventana.
        this.camera = new OrthographicCamera(vpWidth, vpHeight);

        // FitViewport sirve para que al redimensionar el tamaño de la ventana se mantenga la escala.
        viewport = new FitViewport(vpWidth, vpHeight, camera);
        viewport.apply();

        // Guardamos el tamaño de la ventana.
        vpWidth = viewport.getScreenWidth();
        vpHeight = viewport.getScreenHeight();

        // Seteamos la posicion de la camara en la mitad de la ventana.
        camera.position.set(vpWidth / 2, vpHeight / 2, 0);
        font = new BitmapFont();
        ////////////////////////////////////////////////////////////////////////////////

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("map/tiledmap.tmx");
        rendererMap = new OrthogonalTiledMapRenderer(map, batch);
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
        rendererMap.setView(camera);
    }

    @Override
    public void render(float delta) { // Delta = Tiempo entre frame y frame.

        // Llamamos al metodo update enviandole el valor delta.
        update(delta);

        // Limpiamos la escena y le establecemos un fondo de color.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Renderizamos el mapa fuera del batch ya que este lo vuelve a iniciar y terminar.
        rendererMap.render();

        // Al batch le va a setear la matriz de la camara y inicia el batch.
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        // Mostramos los fps en la esquina izquierda-arriba de la ventana.
        drawText(font, batch, "FPS:" + Integer.toString(Gdx.graphics.getFramesPerSecond()), /*-(vpWidth / 2) + 10*/ 0, /*(vpHeight / 2) - 10*/ 0, 1f, 1f, 1f, 1f);

        // Fin del batch.
        batch.end();
    }

    @Override
    public void resize(int width, int height) {

        // Actualizamos el viewport
        viewport.update(width, height);

        // Actualizamos las variables del tamaño de la ventana.
        vpWidth = viewport.getWorldWidth();
        vpHeight = viewport.getWorldHeight();
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

        // Seteamos el color del texto a dibujar
        font.setColor(r, g, b, a);

        // Dibujamos la fuente y le seteamos la posicion en el centro de la camara + x o y
        font.draw(batch, text, camera.position.x + x, camera.position.y + y);
    }

    private void handleInput(float delta) {

        // Multiplicamos la velocidad base y delta para que la velocidad no dependa de los frames.
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
    }
}
