package com.github.mdsimmo.pixxit.operations;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.utils.Clipboard;
import com.github.mdsimmo.pixxit.utils.KeyCode;

public class Paste implements Operation {

    @Override
    public KeyCode keyCode() {
        return KeyCode.any(
                new KeyCode( Input.Keys.CONTROL_LEFT, Input.Keys.V ),
                new KeyCode( Input.Keys.ALT_LEFT, Input.Keys.CONTROL_LEFT, Input.Keys.V )
        );
    }

    @Override
    public void apply( Drawing drawing ) {
        Canvas canvas = Clipboard.getContent();
        if (canvas == null)
            return;
        Canvas layer = drawing.newLayer();
        layer.drawCanvas( canvas, 0, 0 );
    }
}
