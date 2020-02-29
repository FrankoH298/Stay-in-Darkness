package com.stayinthedarkness.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.stayinthedarkness.MainGame;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenu implements Screen {

    private final MainGame game;
    private final Stage stage;
    private final Table table; // Tabla de ordenamiento de widgets (Buttons, labels, etc)
    private final Skin skin;

    public MainMenu(final MainGame game) {
        this.game = game;

        // Seteamos para el que stage controle la entrada del teclado.
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // Creamos la ventana.
        table = new Table();

        // Necesario para que la tabla ocupe toda la ventana.
        table.setFillParent(true);

        // Muestra los bordes de los widgets.
        table.setDebug(false);
        stage.addActor(table);

        // Creamos una skin necesaria para los widgets.
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        // Cargamos los archivos de traduccion.
        I18NBundle bundle = I18NBundle.createBundle(Gdx.files.internal("locale/locale"));

        //---------------------------------Widgets----------------------------------
        TextButton playButton = new TextButton(bundle.get("MainMenu.playButton"), skin);
        TextButton optionsButton = new TextButton(bundle.get("MainMenu.optionButton"), skin);
        TextButton exitButton = new TextButton(bundle.get("MainMenu.exitButton"), skin);
        Label labelName = new Label("Stay In Darkness", skin);
        TextField fieldName = new TextField(bundle.get("MainMenu.fieldName"), skin);
        //---------------------------------Widgets----------------------------------

        //-----------------------------WidgetsListener------------------------------
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }

        });

        //Esto es para cambiar a la ventana del juego
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        //Esto es para cambiar a la ventana de opciones
        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(nombre de la ventana);
            }

        });
        //-----------------------------WidgetsListener------------------------------

        //----------------------------WidgetsParameters-----------------------------
        table.add(labelName).expand(0, 100).prefWidth(100).prefHeight(50);
        table.row(); // Inserta una fila
        table.add(fieldName).expandY().bottom().prefWidth(100).prefHeight(50);
        table.row(); // Inserta una fila
        table.add(playButton).expandY().prefWidth(100).prefHeight(50);
        table.row(); // Inserta una fila
        table.add(optionsButton).expandY().top().prefWidth(100).prefHeight(50);
        table.row();
        table.add(exitButton).expand().bottom().right().prefWidth(100).prefHeight(50);
        //----------------------------WidgetsParameters-----------------------------
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {// delta = Tiempo que hay entre un frame y otro. Ej:   Frame1 -- 50ms -- Frame2

        // Limpiamos la escena y le establecemos un fondo de color.
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Inicio del batch, a partir de aca van todos los .draw().
        game.getSpriteBatch().begin();

        // Actualizamos el stage mandandole delta y dibujamos el stage.
        stage.act(delta);
        stage.draw();

        // Fin del batch, a partir de aca no se dibuja nada que funcione con batch.
        game.getSpriteBatch().end();
    }

    @Override
    public void resize(int width, int height) {

        // Redimensionamos el stage para que ocupe la ventana.
        stage.getViewport().update(width, height, true);
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

        // Limpiamos el stage.
        stage.dispose();
    }

}
