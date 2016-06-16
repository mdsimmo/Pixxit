package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.Selection;
import com.github.mdsimmo.pixxit.utils.KeyCode;
import com.github.mdsimmo.pixxit.utils.Point;

public class Cut implements Operation {

    private Operation copy = new Copy();
    private Operation delete = new Delete();

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.X );
    }

    @Override
    public void apply( Drawing drawing ) {
        copy.apply( drawing );
        delete.apply( drawing );
    }
}
