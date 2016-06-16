package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;
import com.github.mdsimmo.pixxit.utils.Point;

public class Delete implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.FORWARD_DEL );
    }

    @Override
    public void apply( Drawing drawing ) {
        Canvas active = drawing.activeLayer();
        for ( Point p : drawing.selection() ) {
            active.clear( p.x, p.y );
        }
    }
}
