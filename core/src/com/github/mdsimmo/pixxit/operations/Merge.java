package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;

import java.util.Iterator;

public class Merge implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.SHIFT_LEFT, Input.Keys.M );
    }

    @Override
    public void apply( Drawing drawing ) {
        Canvas merged = drawing.newLayer();
        Iterator<Canvas> iterator = drawing.iterator();
        while (iterator.hasNext()) {
            Canvas layer = iterator.next();
            if (layer != merged) {
                merged.drawCanvas( layer, 0, 0 );
                layer.dispose();
                iterator.remove();
            }
        }
    }
}
