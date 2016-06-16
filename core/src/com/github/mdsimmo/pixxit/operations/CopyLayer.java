package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.Clipboard;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class CopyLayer implements Operation {

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.SHIFT_LEFT, Input.Keys.CONTROL_LEFT, Input.Keys.C );
    }

    @Override
    public void apply( Drawing drawing ) {
        Canvas active = drawing.activeLayer();
        Canvas clone = new Canvas( active.width(), active.height() );
        clone.drawCanvas( active, 0, 0 );
        Clipboard.setContent( clone );
    }
}
