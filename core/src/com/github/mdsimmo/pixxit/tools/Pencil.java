package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;
import com.github.mdsimmo.pixxit.utils.ShapeIterator;
import com.github.mdsimmo.pixxit.utils.Point;

public class Pencil extends BrushTool {

    @Override
    protected void drawLine( int xFrom, int yFrom, int xTo, int yTo, Drawing drawing, Cursor c ) {
        Color clr = c.button() == Cursor.Button.LEFT ? drawing.primaryColor() : drawing.secondaryColor();
        for ( Point p : ShapeIterator.line( xFrom, yFrom, xTo, yTo )) {
            //drawing.activeLayer().drawCircle( p.x, p.y, 0, clr, null );
            drawing.activeLayer().draw( p.x, p.y, clr );
        }
    }

    @Override
    public String name() {
        return "pencil";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.P );
    }
}
