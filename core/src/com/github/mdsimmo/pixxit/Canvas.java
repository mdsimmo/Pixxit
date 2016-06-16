package com.github.mdsimmo.pixxit;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.github.mdsimmo.pixxit.utils.Color;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class Canvas implements Disposable {

    /**
     * Defines how draw calls will effect the pixel they are changing
     */
    public enum BlendMode {
        /**
         * Ignore the pixel underneath. Make the new pixel the exact same as the old one
         */
        REPLACE,
        /**
         * Draw the new pixels over the old one (this is "normal" blending)
         */
        SOURCE_OVER
    }

    final int width, height;
    final Texture texture;
    final Pixmap image;
    private boolean dirty = true;
    BlendMode blendMode = BlendMode.SOURCE_OVER;

    public Canvas( int width, int height ) {
        this.width = width;
        this.height = height;
        image = new Pixmap( width, height, Pixmap.Format.RGBA8888 );
        image.setColor( Color.TRANSPARENT.toRGBA() );
        Pixmap.setBlending( Pixmap.Blending.None );
        image.fill();
        Pixmap.setBlending( Pixmap.Blending.SourceOver );
        texture = new Texture( image );
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void drawCanvas( Canvas canvas, int x, int y ) {
        correctBlendMode();
        image.drawPixmap( canvas.image, x, y );
        dirty = true;
    }

    public void draw( int x, int y, Color color ) {
        correctBlendMode();
        image.drawPixel( x, y, color.toRGBA() );
        dirty = true;
    }

    public void clear( int x, int y ) {
        Pixmap.setBlending( Pixmap.Blending.None );
        image.drawPixel( x, y, Color.TRANSPARENT.toRGBA() );
        dirty = true;
    }

    public void drawLine( int x1, int y1, int x2, int y2, Color color ) {
        correctBlendMode();
        image.setColor( color.toRGBA() );
        image.drawLine( x1, y1, x2, y2 );
        dirty = true;
    }

    public void drawRectangle(int x1, int y1, int x2, int y2, Color border, Color fill) {
        int width = x2 - x1;
        int height = y2 - y1;
        int x = x1;
        int y = y1;
        if ( width < 0 ) {
            x = x2;
            width *= -1;
        }
        if ( height < 0 ) {
            y = y2;
            height *= -1;
        }
        correctBlendMode();
        if (fill != null) {
            image.setColor( fill.toRGBA() );
            image.fillRectangle( x, y, width, height );
        }
        if (border != null) {
            image.setColor( border.toRGBA() );
            image.drawRectangle( x, y, width, height );
        }
        dirty = true;
    }

    public void drawCircle( int x, int y, int radius, Color fill, Color line ) {
        correctBlendMode();
        if ( fill != null ) {
            image.setColor( fill.toRGBA() );
            image.fillCircle( x, y, radius );
        }
        if ( line != null ) {
            image.setColor( line.toRGBA() );
            image.drawCircle( x, y, radius );
        }
        dirty = true;
    }

    /**
     * Fills in the entire image
     */
    public void fill( Color c ) {
        correctBlendMode();
        image.setColor( c.toRGBA() );
        image.fill();
        dirty = true;
    }

    /**
     * Clears everything from the image. The canvas will be fully transparent afterwards.
     */
    public void clear() {
        Pixmap.setBlending( Pixmap.Blending.None );
        image.setColor( Color.TRANSPARENT.toRGBA() );
        image.fill();
        dirty = true;
    }

    /**
     * Configures LibGDX's Pixmap so it uses the same blending mode as this canvas. Because LibGDX
     * uses a global state (underneath it just calls a OpenGL method), we need to make sure this is
     * called at the start of <em>every</em> draw call.
     */
    private void correctBlendMode() {
        Pixmap.Blending pixmap = Pixmap.getBlending();
        BlendMode blendMode = this.blendMode;
        if ( pixmap == Pixmap.Blending.None ) {
            if ( blendMode == BlendMode.SOURCE_OVER ) {
                Pixmap.setBlending( Pixmap.Blending.SourceOver );
            }
        } else {
            if ( blendMode == BlendMode.REPLACE ) {
                Pixmap.setBlending( Pixmap.Blending.None );
            }
        }
    }

    /**
     * Gets the color of the pixel at the given position
     * @param x the x position
     * @param y the y position
     * @return the color. Null if the co-ordinates are outside the image
     */
    public Color getColor( int x, int y ) {
        if (x < 0 || y < 0 || x >= image.getWidth() || y >= image.getHeight())
            return null;
        return Color.rgba( image.getPixel( x, y ) );
    }

    public void render( Batch batch, float x, float y, float scale ) {
        if ( dirty ) {
            TextureData data = new PixmapTextureData( image, image.getFormat(), false, false );
            texture.load( data );
            dirty = false;
        }

        batch.draw( texture, x, y, 0, 0, width, height, scale, scale, 0, 0, 0, width, height, false, true );
    }

    @Override
    public void dispose() {
        image.dispose();
        texture.dispose();
    }

    /**
     * Many tools require the blend mode to be left in the "SOURCE_OVER" mode. Thus, if the mode is altered
     * from this mode, make sure the mode is restored afterwards.
     * @param mode the new mode to usex
     */
    public void setBlendMode( BlendMode mode ) {
        this.blendMode = mode;
    }

    public BlendMode getBlendMode() {
        return blendMode;
    }

}
