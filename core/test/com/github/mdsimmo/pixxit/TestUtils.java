package com.github.mdsimmo.pixxit;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;

import static org.mockito.Mockito.*;

public class TestUtils {

    static {
        // initialise a dummy gdx
        final HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication( new ApplicationAdapter() {}, config );
        Gdx.gl = mock( GL20.class );
    }

    public static void initGDX() {
        // only needs tp run the static block above once
    }

}
