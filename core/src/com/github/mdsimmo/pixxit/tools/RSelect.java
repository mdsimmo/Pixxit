package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class RSelect implements Tool {

    private Color HIGHLIGHT = Color.i( 255, 255, 250, 50 );

    @Override
    public void started( Drawing drawing, Cursor cursor ) {
    }

    @Override
    public void activate( Drawing drawing, Cursor cursor ) {
        Canvas temp = drawing.tempLayer();
        temp.clear();
        temp.drawRectangle( cursor.xStart(), cursor.yStart(), cursor.x(), cursor.y(), null, HIGHLIGHT );
    }

    @Override
    public void stopped( Drawing drawing, Cursor cursor ) {
        boolean addToSelection = !Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT );

        if ( !Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT )
                && !Gdx.input.isKeyPressed( Input.Keys.SHIFT_LEFT ) ) {
            // start anew
            drawing.selection().clear();
        }

        drawing.selection().setRectangle(
                cursor.xStart(), cursor.yStart(),
                cursor.x() - cursor.xStart(), cursor.y() - cursor.yStart(), addToSelection);

        // delete the temp buffer
        drawing.tempLayer().clear();
    }

    @Override
    public String name() {
        return "rectangle-select";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.S );
    }
}
