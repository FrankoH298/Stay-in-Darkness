package com.stayinthedarkness.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stayinthedarkness.MainGame;
import com.stayinthedarkness.entities.Dynamic.Player;
import com.stayinthedarkness.entities.Solid.Tree;
import com.stayinthedarkness.world.TiledMapSiD;
import com.stayinthedarkness.world.WorldPosition;

import java.util.ArrayList;

public class GameScreen implements Screen {

    // VARIABLES
    private final MainGame game;
    private final SpriteBatch batch;
    private final OrthographicCamera camera;
    private final Viewport viewPort;
    private final BitmapFont font;
    private final TiledMapSiD tiledMapSiD;
    private Player myPlayer;
    private Player secondPlayer;
    private ArrayList<Player> players;


    public GameScreen(MainGame game) {
        this.game = game;
        this.batch = game.getSpriteBatch();

        // Creamos una camara.
        this.camera = new OrthographicCamera();

        // FitViewport sirve para que al redimensionar el tamaño de la ventana se mantenga la escala.
        viewPort = new ScalingViewport(Scaling.fit, MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        viewPort.apply();

        // Seteamos la posicion de la camara en la mitad de la ventana.
        camera.position.set(viewPort.getWorldWidth() / 2f, viewPort.getWorldHeight() / 2f, 0);

        font = new BitmapFont();
        ////////////////////////////////////////////////////////////////////////////////
        tiledMapSiD = new TiledMapSiD(batch);

        myPlayer = new Player(0, 1, 0, 0);
        secondPlayer = new Player(1, 1, 50, 100);

        players = new ArrayList<Player>();

        players.add(myPlayer);
        players.add(secondPlayer);
    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        // Procesamos la entrada
        handleInput(delta);
        // Actualiza la camara.
        camera.position.set(myPlayer.getPosition().getX() + myPlayer.getCenterPositionW(delta), myPlayer.getPosition().getY() + myPlayer.getCenterPositionH(delta), 0);
        camera.update();

        // Le seteamos la camara al renderizado del mapa.
        tiledMapSiD.getRendererMap().setView(camera);

        myPlayer.update(delta);
        secondPlayer.update(delta);
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
        drawText(font, batch, "FPS:" + Integer.toString(Gdx.graphics.getFramesPerSecond()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 10f, 1f, 1f, 1f, 1f);

        // Renderizamos modo debug. (Muestra posiciones).
        renderDebug();

        myPlayer.render(batch);
        secondPlayer.render(batch);
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
        float velocity = (myPlayer.getVelocity() * delta);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            myPlayer.setHeading(1);
            myPlayer.translate(0, velocity);
            myPlayer.updateStateTimer(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            myPlayer.setHeading(0);
            myPlayer.translate(0, -velocity);
            myPlayer.updateStateTimer(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            myPlayer.setHeading(3);
            myPlayer.translate(velocity, 0);
            myPlayer.updateStateTimer(delta);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            myPlayer.setHeading(2);
            myPlayer.translate(-velocity, 0);
            myPlayer.updateStateTimer(delta);
        } else {
            if (myPlayer.getStateTimer() != 0) {
                myPlayer.setStateTimer(0);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenu(game));
        }
    }


    private void renderDebug() {
        drawText(font, batch, "CamX:" + Float.toString(camera.position.x), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 30f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "CamY:" + Float.toString(camera.position.y), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 50f, 1f, 1f, 1f, 1f);


        for (int a = 0; a < 2; a++) {
            drawText(font, batch, "Player" + a + " posX:" + players.get(a).getPosition().getX(), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 70f - (40f * a), 1f, 1f, 1f, 1f);
            drawText(font, batch, "Player" + a + " posY:" + players.get(a).getPosition().getY(), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 90f - (40f * a), 1f, 1f, 1f, 1f);
        }

        drawText(font, batch, "vpWorldWidth:" + Float.toString(viewPort.getWorldWidth()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 150f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "vpWorldHeight:" + Float.toString(viewPort.getWorldHeight()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 170f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "vpScreenWidth:" + Float.toString(viewPort.getScreenWidth()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 190f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "vpScreenHeight:" + Float.toString(viewPort.getScreenHeight()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 210f, 1f, 1f, 1f, 1f);

    }
}
