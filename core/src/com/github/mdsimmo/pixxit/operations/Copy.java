package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.Selection;
import com.github.mdsimmo.pixxit.utils.Clipboard;
import com.github.mdsimmo.pixxit.utils.KeyCode;
import com.github.mdsimmo.pixxit.utils.Point;

public class Copy implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.C );
    }

    @Override
    public void apply( Drawing drawing ) {
        Selection selection = drawing.selection();
        Canvas active = drawing.activeLayer();
        Canvas canvas = new Canvas( active.width(), active.height() );
        for ( Point p : selection ) {
            canvas.draw( p.x, p.y, active.getColor( p.x, p.y ) );
        }
        Clipboard.setContent( canvas );
    }
}
