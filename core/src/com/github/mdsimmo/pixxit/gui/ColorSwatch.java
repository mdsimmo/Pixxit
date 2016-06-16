package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.Value;

/**
 * Displays a color to the user and can have that color dynamically change
 */
public class ColorSwatch extends Image {

    private Color lastColor = null;
    private Value<Color> value;

    public ColorSwatch(Color c, int size) {
        this(() -> c, size);
    }

    public ColorSwatch( Value<Color> c, int size ) {
        this.value = c;
        Pixmap pix = new Pixmap( size, size, Pixmap.Format.RGB888 );
        pix.setColor( Color.WHITE.toRGBA() );
        pix.fill();
        Texture tex = new Texture( pix );
        setDrawable( new TextureRegionDrawable( new TextureRegion( tex ) ) );
        pix.dispose();
    }

    public void setColor( Color c ) {
        setColor( () -> c );
    }

    public void setColor( Value<Color> c) {
        this.value = c;
    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {
        Color c = value.get();
        if (c != lastColor) {
            this.setColor( c.rf, c.gf, c.bf, c.af );
            lastColor = c;
        }
        super.draw( batch, parentAlpha );
    }
}
