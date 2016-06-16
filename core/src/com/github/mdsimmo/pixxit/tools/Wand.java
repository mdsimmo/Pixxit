package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Selection;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;
import com.github.mdsimmo.pixxit.utils.Point;
import com.github.mdsimmo.pixxit.utils.ShapeIterator;

public class Wand implements Tool {

    @Override
    public void started( Drawing drawing, Cursor cursor ) {

    }

    @Override
    public void activate( Drawing drawing, Cursor cursor ) {

    }

    @Override
    public void stopped( Drawing drawing, Cursor cursor ) {
        Selection selection = drawing.selection();
        if ( !Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT ) && !Gdx.input.isKeyPressed( Input.Keys.SHIFT_LEFT ) )
            selection.clear();
        boolean add = !Gdx.input.isKeyPressed( Input.Keys.CONTROL_LEFT );
        for ( Point p : ShapeIterator.area( cursor.x(), cursor.y(), drawing.activeLayer(), drawing.tempLayer() )) {
            selection.setSelected( p.x, p.y, add );
        }
    }

    @Override
    public String name() {
        return "wand";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.W );
    }
}
