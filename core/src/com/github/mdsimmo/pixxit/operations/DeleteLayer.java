package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class DeleteLayer implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.FORWARD_DEL, Input.Keys.SHIFT_LEFT );
    }

    @Override
    public void apply( Drawing drawing ) {
        drawing.remove( drawing.layers().indexOf( drawing.activeLayer() ) );
    }
}
