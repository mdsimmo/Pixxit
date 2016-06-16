package com.github.mdsimmo.pixxit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;
import com.github.mdsimmo.pixxit.utils.Point;

import java.util.Iterator;

/**
 * A selection highlights an arbitrary selection of a rectangle
 */
public class Selection implements Iterable<Point>, Disposable {

    private final Texture texture;
    final Pixmap data;
    private final int width, height;

    private final Color ON = new Color( 1, 1, 1, 0.2f ), OFF = new Color( 0, 0, 0, 0 );

    public Selection( int width, int height ) {
        this.width = width;
        this.height = height;
        this.data = new Pixmap( width, height, Pixmap.Format.RGBA8888 );
        data.setColor( OFF );
        data.fill();
        texture = new Texture( data );
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public boolean isSelected( int x, int y ) {
        return isSelected( data.getPixel( x, y ) );
    }

    private boolean isSelected( int rgba ) {
        return (rgba & 0x0000FF00) != 0;
    }

    public void setSelected( int x, int y, boolean selected ) {
        Pixmap.setBlending( Pixmap.Blending.None );
        data.setColor( selected ? ON : OFF );
        data.drawPixel( x, y );
    }

    /**
     * Sets a rectangular region to be selected. Negative widths and heights are supported
     */
    public void setRectangle( int x, int y, int width, int height, boolean selected ) {
        Pixmap.setBlending( Pixmap.Blending.None );
        data.setColor( selected ? ON : OFF );
        if ( width < 0 )
            x -= (width *= -1);
        if ( height < 0 )
            y -= (height *= -1);
        data.fillRectangle( x, y, width, height );
    }

    public void clear() {
        Pixmap.setBlending( Pixmap.Blending.None );
        data.setColor( OFF );
        data.fill();
    }

    public void selectAll() {
        Pixmap.setBlending( Pixmap.Blending.None );
        data.setColor( ON );
        data.fill();
    }

    public void render( Batch batch, float x, float y, float scale ) {
        texture.draw( data, 0, 0 );
        batch.draw( texture, x, y, 0, 0, width, height, scale, scale, 0, 0, 0, width, height, false, true );
    }

    @Override
    public void dispose() {
        data.dispose();
        texture.dispose();
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            Point p = new Point(-1, 0);

            @Override
            public boolean hasNext() {
                int width = width();
                int height = height();
                // complete the first row
                int y = p.y;
                for ( int x = p.x+1; x < width; x++ ) {
                    if (isSelected( x, y )) {
                        p.set( x, y );
                        return true;
                    }
                }
                // scan other rows
                for ( y += 1; y < height; y++ ) {
                    for ( int x = 0; x < width; x++ ) {
                        if (isSelected( x, y )) {
                            p.set( x, y );
                            return true;
                        }
                    }
                }

                // nothing found
                return false;
            }

            @Override
            public Point next() {
                return p;
            }
        };
    }
}
