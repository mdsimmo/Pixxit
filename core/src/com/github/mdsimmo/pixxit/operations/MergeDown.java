package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class MergeDown implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.M );
    }

    @Override
    public void apply( Drawing drawing ) {
        Canvas active = drawing.activeLayer();
        int activeIndex = drawing.layers().indexOf( active );

        // only merge if there is a layer below
        if ( activeIndex == 0 )
            return;

        Canvas base = drawing.layers().get( activeIndex-1 );

        // combine the base and active onto the active (through a temporary layer)
        Canvas merged = new Canvas( drawing.width(), drawing.height() );
        merged.drawCanvas( base, 0, 0 );
        merged.drawCanvas( active, 0, 0 );
        active.clear();
        active.drawCanvas( merged, 0, 0 );

        // remove the layer under
        drawing.remove( activeIndex-1 ).dispose();
    }
}
