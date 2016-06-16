package com.github.mdsimmo.pixxit.gui.views;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * For private use by the DrawingView. This class reads the user input and moves the camera around.
 */
class CameraMover extends InputListener {

    private final OrthographicCamera camera;
    private float xPre, yPre;

    public CameraMover( OrthographicCamera camera ) {
        this.camera = camera;
    }

    @Override
    public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
        if (button != Input.Buttons.MIDDLE)
            return false;
        xPre = x;
        yPre = y;
        return true;
    }

    @Override
    public void touchDragged( InputEvent event, float x, float y, int pointer ) {
        moved( x, y );
    }

    @Override
    public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
        moved( x, y );
    }

    @Override
    public boolean scrolled( InputEvent event, float x, float y, int amount ) {
        camera.zoom -= camera.zoom * amount * 0.1f;
        return true;
    }

    private void moved( float x, float y ) {
        camera.position.add( x- xPre, y- yPre, 0 );
        xPre = x;
        yPre = y;
    }
}