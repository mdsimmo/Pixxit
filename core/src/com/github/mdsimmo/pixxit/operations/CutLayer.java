package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class CutLayer implements Operation {

    private Operation copy = new CopyLayer();
    private Operation delete = new DeleteLayer();

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.SHIFT_LEFT, Input.Keys.X );
    }

    @Override
    public void apply( Drawing drawing ) {
        copy.apply( drawing );
        delete.apply( drawing );
    }
}
