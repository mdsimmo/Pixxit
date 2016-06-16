package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.CanvasIO;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class Save implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.S );
    }

    @Override
    public void apply( Drawing drawing ) {
        Canvas merged = new Canvas( drawing.width(), drawing.height() );
        for ( Canvas layer : drawing ) {
            merged.drawCanvas( layer, 0, 0 );
        }
        // todo let the user select where they want to save
        CanvasIO.writePNG( merged, Gdx.files.local( "save.png" ) );
        merged.dispose();
    }
}
