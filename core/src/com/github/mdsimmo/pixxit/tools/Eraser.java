package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class Eraser extends BrushTool {

    @Override
    protected void drawLine( int xFrom, int yFrom, int xTo, int yTo, Drawing drawing, Cursor c ) {
        Canvas canvas = drawing.activeLayer();

        if ( c.button() == Cursor.Button.LEFT ) {
            // erase the part
            canvas.setBlendMode( Canvas.BlendMode.REPLACE );
            canvas.drawLine( xFrom, yFrom, xTo, yTo, Color.TRANSPARENT );
            canvas.setBlendMode( Canvas.BlendMode.SOURCE_OVER );
        } else {
            // draw in the secondary color
            canvas.drawLine( xFrom, yFrom, xTo, yTo, drawing.secondaryColor() );
        }
    }

    @Override
    public String name() {
        return "eraser";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.E );
    }
}
