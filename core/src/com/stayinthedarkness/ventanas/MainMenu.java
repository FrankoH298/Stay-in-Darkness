package com.stayinthedarkness.ventanas;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.stayinthedarkness.StayintheDarkness;

/**
 *
 * @author franc
 */
public class MainMenu implements Screen {

    private final StayintheDarkness game;
    private final Stage stage;
    private final Table table; // Tabla de ordenamiento de widgets (Buttons, labels, etc)
    private final Skin skin;
    private String name;
    
            
            
    public MainMenu(StayintheDarkness game) {
        this.game = game;
        //-------------------------------Stage------------------------------------
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Seteamos para el que stage controle la entrada del teclado.
        //-------------------------------Tabla------------------------------------
        table = new Table();
        table.setFillParent(true); // Necesario para que la tabla ocupe toda la ventana.
        table.setDebug(false); // Muestra los bordes de los widgets
        stage.addActor(table);
        //-----------------------------Skins--------------------------------------
        skin = new Skin(Gdx.files.internal("uiskin.json")); // Creamos una skin necesaria para los widgets.
        //---------------------------Widgets----------------------------------------
        TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options",skin);
        final TextButton exitButton = new TextButton("Exit",skin);
        Label labelName = new Label("Stay In Darkness", skin);
        TextField fieldName = new TextField("Ingrese su nombre", skin);
        
        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
               Gdx.app.exit();
            }
            
        });
        //Esto es para cambiar a la ventana del juego
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(Nombre de la Ventana);
            }
        });
        
        //Esto es para cambiar a la venatana de opciones
        optionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //game.setScreen(nombre de la ventana);
            }
            
        });
        
        
        table.add(labelName).expand(0,100).prefWidth(100).prefHeight(50);
        table.row(); // Inserta una fila
        table.add(fieldName).expandY().bottom().prefWidth(100).prefHeight(50);
        table.row(); // Inserta una fila
        table.add(playButton).expandY().prefWidth(100).prefHeight(50);
        table.row(); // Inserta una fila 
        table.add(optionsButton).expandY().top().prefWidth(100).prefHeight(50);
        table.row();
        table.add(exitButton).expand().bottom().right().prefWidth(100).prefHeight(50);
                
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // delta = Tiempo que hay entre un frame y otro. Ej:   Frame1 -- 50ms -- Frame2
        //-------------------------------------------------------------------
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f); // Limpiamos la escena y le establecemos un fondo de color.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //-------------------------------------------------------------------
        game.getSpriteBatch().begin(); // Inicio del batch, a partir de aca van todos los .draw().
        stage.act(delta); // Actualizamos el stage mandandole delta.
        stage.draw(); // Dibujamos el stage con la tabla y sus respectivos widgets.
        game.getSpriteBatch().end(); // Fin del batch, no se dibuja mas y le enviamos el frame a la gpu.
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true); // Redimensionamos el stage para que ocupe la ventana.
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
        stage.dispose(); // Limpiamos el stage.
    }

}
