package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class DeselectAll implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.SHIFT_LEFT, Input.Keys.A );
    }

    @Override
    public void apply( Drawing drawing ) {
        drawing.selection().clear();
    }
}
