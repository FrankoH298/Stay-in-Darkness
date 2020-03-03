package com.stayinthedarkness.objects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.stayinthedarkness.screens.GameScreen;

public class Console {
    private Array<String> console;
    private GameScreen game;

    public Console(GameScreen game, int size) {
        this.game = game;
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

    public void renderConsole(SpriteBatch batch) {
        for (int a = 0; a < console.size; a++) {
            game.drawText(game.getFont(), batch, console.get(a), (-(game.getViewport().getWorldWidth() / 2) + 10f), (-(game.getViewport().getWorldHeight() / 2) + 30f + (20f * a)), 1f, 1f, 1f, 1f - (0.2f * a));
        }
    }

}
