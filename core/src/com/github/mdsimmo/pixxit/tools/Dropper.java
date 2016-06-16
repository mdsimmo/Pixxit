package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;

/**
 * The eye dropper tool. Selects a color from the canvas
 */
public class Dropper implements Tool {

    @Override
    public void started( Drawing drawing, Cursor cursor ) {

    }

    @Override
    public void activate( Drawing drawing, Cursor cursor ) {

    }

    @Override
    public void stopped( Drawing drawing, Cursor cursor ) {
        Color c = drawing.activeLayer().getColor( cursor.x(), cursor.y() );
        if ( c == null )
            return;
        if (cursor.button() == Cursor.Button.LEFT )
            drawing.setPrimaryColor( c );
        else
            drawing.setSecondaryColor( c );
    }

    @Override
    public String name() {
        return "dropper";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.D );
    }
}
