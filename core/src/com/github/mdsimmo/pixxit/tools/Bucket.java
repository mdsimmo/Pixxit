package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;
import com.github.mdsimmo.pixxit.utils.Point;
import com.github.mdsimmo.pixxit.utils.ShapeIterator;

import java.util.ArrayList;

public class Bucket implements Tool {

    class Node {
        final int x, y;
        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void started( Drawing drawing, Cursor cursor ) {
    }

    @Override
    public void activate( Drawing drawing, Cursor cursor ) {
    }

    @Override
    public void stopped( Drawing drawing, Cursor cursor ) {
        Canvas canvas = drawing.activeLayer();
        Color clr = cursor.button() == Cursor.Button.LEFT ? drawing.primaryColor() : drawing.secondaryColor();
        for ( Point p : ShapeIterator.area( cursor.x(), cursor.y(), canvas, drawing.tempLayer() ) )
            canvas.draw( p.x, p.y, clr );
    }

    @Override
    public String name() {
        return "bucket";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.B );
    }
}
