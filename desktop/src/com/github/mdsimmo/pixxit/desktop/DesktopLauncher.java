package com.github.mdsimmo.pixxit.desktop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.mdsimmo.pixxit.Pixxit;

public class DesktopLauncher
{
    public static void main ( String[] args )
    {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Pixxit";
        cfg.width = 800;
        cfg.height = 500;
        new LwjglApplication(new Pixxit(), cfg);
    }

}