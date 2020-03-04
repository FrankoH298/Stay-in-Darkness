package com.stayinthedarkness.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.stayinthedarkness.MainGame;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScreen implements Screen {

    private final MainGame game;
    private final Stage stage;
    private final Table table; // Tabla de ordenamiento de widgets (Buttons, labels, etc)
    private final Skin skin;
    private CheckBox fullscreenCheckBox;
    private Label fullscreenLabel;
    private Label resolutionLabel;
    private Label actualResLabel;
    private TextButton previousResolutionBtn;
    private TextButton nextResolutionBtn;
    private Label labelSettings;
    private final I18NBundle bundle;
    private TextButton BackButton;
    private Slider soundSlider;
    private Label soundLabel;

    public OptionsScreen(final MainGame game) {
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
        bundle = I18NBundle.createBundle(Gdx.files.internal("locale/locale"));

        // Inicializamos los widgets, seteamos los listeners y aplicamos parametros
        widgetsInit();
        widgetsListeners();
        widgetsParameters();

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

    private void widgetsInit() {
        fullscreenCheckBox = new CheckBox("", skin);
        fullscreenLabel = new Label(bundle.get("MainMenu.fullscreenLabel"), skin);
        resolutionLabel = new Label(bundle.get("MainMenu.resolutionLabel"), skin);
        actualResLabel = new Label("e.x 1920x1080", skin);
        previousResolutionBtn = new TextButton("<", skin);
        nextResolutionBtn = new TextButton(">", skin);
        BackButton = new TextButton(bundle.get("MainMenu.BackButton"), skin);
        labelSettings = new Label(bundle.get("MainMenu.labelSettings"), skin);
        soundSlider = new Slider(0, 100, 1, false, skin);
        soundLabel = new Label(bundle.get("MainMenu.soundLabel"), skin);

    }

    private void widgetsListeners() {
        BackButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenu(game));
            }

        });

        //Esto es para cambiar a la ventana del juego
        fullscreenCheckBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                if (fullscreenCheckBox.isChecked() == false) {
                    Gdx.graphics.setWindowedMode(MainGame.V_WIDTH, MainGame.V_HEIGHT);
                } else {
                    Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
                // Creamos un nuevo viewport con el nuevo tamaño de la ventana.
                reloadViewport();
            }
        });


    }

    private void widgetsParameters() {
        table.add(labelSettings).colspan(4).expand(Gdx.graphics.getWidth(), 1);
        table.row(); // Inserta una fila
        table.add(fullscreenLabel).expand().right();
        table.add(fullscreenCheckBox).expand().colspan(3).size(100);
        table.row(); // Inserta una fila
        table.add(resolutionLabel).expand().right();
        table.add(previousResolutionBtn).expand().width(20).height(30).right();
        table.add(actualResLabel).expand();
        table.add(nextResolutionBtn).expand().width(20).height(30).left();
        table.row();
        table.add(soundLabel).expand().right();
        table.add(soundSlider).expand().colspan(3);
        table.row();
        table.add(BackButton).expand().left().bottom().width(100).height(50);

    }

    private void reloadViewport() {
        stage.setViewport(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }
}
