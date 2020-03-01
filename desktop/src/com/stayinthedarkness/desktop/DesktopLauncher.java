package com.stayinthedarkness.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stayinthedarkness.MainGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.foregroundFPS = 0;
        config.backgroundFPS = 0;
        config.vSyncEnabled = false;
        config.allowSoftwareMode = false;
        config.width = MainGame.V_WIDTH;
        config.height = MainGame.V_HEIGHT;
        config.title = "Stay in Darkness";
        config.resizable = false;
        new LwjglApplication(new MainGame(), config);

    }
}
