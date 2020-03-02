package com.stayinthedarkness.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.stayinthedarkness.MainGame;
import com.stayinthedarkness.entities.Dynamic.Player;
import com.stayinthedarkness.entities.Solid.Tree;
import com.stayinthedarkness.network.Packets;
import com.stayinthedarkness.network.SiDClient;
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
    private ArrayList<Player> players;
    private Array<String> console;
    public Array<Array<Animation>> animations;
    private boolean isPlayable = false;
    SiDClient client;

    public GameScreen(MainGame game) {

        this.game = game;
        this.batch = game.getSpriteBatch();

        animations = new Array<Array<Animation>>();
        animations.add(loadAnimation(1));

        // Creamos una camara.
        this.camera = new OrthographicCamera();

        // FitViewport sirve para que al redimensionar el tamaño de la ventana se mantenga la escala.
        viewPort = new ScalingViewport(Scaling.fit, MainGame.V_WIDTH, MainGame.V_HEIGHT, camera);
        viewPort.apply();

        // Cambiamos el input processor para evitar errores.
        Gdx.input.setInputProcessor(new Stage());

        // Seteamos la posicion de la camara en la mitad de la ventana.
        camera.position.set(viewPort.getWorldWidth() / 2f, viewPort.getWorldHeight() / 2f, 0);

        font = new BitmapFont();
        ////////////////////////////////////////////////////////////////////////////////
        tiledMapSiD = new TiledMapSiD(batch);

        // Inicializamos la consola con un tamaño de 4.
        initConsole(4);

        players = new ArrayList<Player>();

        // Conectamos con el servidor.
        client = new SiDClient(game);

    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        // Procesamos la entrada
        handleInput(delta);

        // Actualiza la camara.
        if (isPlayable) {
            camera.position.set(players.get(0).getPosition().x + players.get(0).getCenterPositionW(delta), players.get(0).getPosition().y + players.get(0).getCenterPositionH(delta), 0);
        }
        camera.update();

        // Le seteamos la camara al renderizado del mapa.
        tiledMapSiD.getRendererMap().setView(camera);

        for (int a = 0; a < players.size(); a++) {
            players.get(a).update(delta);
        }
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

        // Renderizamos la consola.
        renderConsole(batch);

        // Renderizamos modo debug. (Muestra posiciones).
        renderDebug();

        for (int a = 0; a < players.size(); a++) {
            players.get(a).render(batch);
        }

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
        if (isPlayable) {
            float velocity = (players.get(0).getVelocity() * delta);
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                players.get(0).setHeading(1);
                players.get(0).translate(0, velocity);
                //players.get(0).updateStateTimer(delta);
                movePlayer(players.get(0));
            } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                players.get(0).setHeading(0);
                players.get(0).translate(0, -velocity);
                //players.get(0).updateStateTimer(delta);
                movePlayer(players.get(0));
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                players.get(0).setHeading(3);
                players.get(0).translate(velocity, 0);
                //players.get(0).updateStateTimer(delta);
                movePlayer(players.get(0));
            } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                players.get(0).setHeading(2);
                players.get(0).translate(-velocity, 0);
                //players.get(0).updateStateTimer(delta);
                movePlayer(players.get(0));
            }

            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
                client.client.close();
                game.setScreen(new MainMenu(game));
            }
        }
    }


    private void renderDebug() {
        drawText(font, batch, "CamX:" + Float.toString(camera.position.x), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 30f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "CamY:" + Float.toString(camera.position.y), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 50f, 1f, 1f, 1f, 1f);

        drawText(font, batch, "vpWorldWidth:" + Float.toString(viewPort.getWorldWidth()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 150f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "vpWorldHeight:" + Float.toString(viewPort.getWorldHeight()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 170f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "vpScreenWidth:" + Float.toString(viewPort.getScreenWidth()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 190f, 1f, 1f, 1f, 1f);
        drawText(font, batch, "vpScreenHeight:" + Float.toString(viewPort.getScreenHeight()), -(viewPort.getWorldWidth() / 2) + 10f, (viewPort.getWorldHeight() / 2) - 210f, 1f, 1f, 1f, 1f);

    }

    private void initConsole(int size) {
        console = new Array<String>();
        for (int a = 0; a < size; a++) {
            console.add("");
        }
    }

    public void consoleTextAdd(String msg) {
        System.out.println("a");
        for (int a = console.size - 1; a > 0; a--) {
            console.set(a, console.get(a - 1));
        }
        console.set(0, msg);
    }

    private void renderConsole(SpriteBatch batch) {
        for (int a = 0; a < console.size; a++) {
            drawText(font, batch, console.get(a), (-(viewPort.getWorldWidth() / 2) + 10f), (-(viewPort.getWorldHeight() / 2) + 30f + (20f * a)), 1f, 1f, 1f, 1f - (0.2f * a));
        }
    }

    private Array<Animation> loadAnimation(int grhNumber) {
        Array<Animation> animations;
        Texture texture = new Texture(Gdx.files.internal("graphics/" + grhNumber + ".png"));
        animations = new Array<Animation>();
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 6; b++) {
                frames.add(new TextureRegion(texture, 27 * b, 47 * a, 27, 47));
            }
            animations.add(new Animation(0.15f, frames));
            frames.clear();
        }
        return animations;
    }

    public void addPlayer(int id, float x, float y, int heading) {
        Player player = new Player(id, x, y, animations.get(0));
        player.setHeading(heading);
        players.add(player);
        if (client.client.getID() == id) {
            isPlayable = true;
        }
    }

    public void removePlayer(int id) {
        for (int a = 0; a < players.size(); a++) {
            if (players.get(a).getId() == id) {
                players.remove(a);
            }
        }
    }

    public void updatePlayer(int id, float x, float y, int heading) {
        for (int a = 0; a < players.size(); a++) {
            if (players.get(a).getId() == id) {
                players.get(a).setHeading(heading);
                players.get(a).setPosition(new WorldPosition(x, y));
            }
        }
    }

    public void movePlayer(Player player) {
        Packets.Packet03UpdatePlayer p = new Packets.Packet03UpdatePlayer();
        p.heading = players.get(0).getHeading();
        p.x = players.get(0).getPosition().x;
        p.y = players.get(0).getPosition().y;
        p.id = players.get(0).getId();
        client.client.sendUDP(p);

    }
}

