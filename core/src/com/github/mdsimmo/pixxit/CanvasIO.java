package com.github.mdsimmo.pixxit;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

public class CanvasIO {

    /**
     * Writes a canvas out to a file
     * @param canvas the canvas to write
     * @param file the file to write it out to
     */
    public static void writePNG( Canvas canvas, FileHandle file ) {
        Pixmap image = canvas.image;
        int width = image.getWidth();
        int height = image.getHeight();

        Pixmap flipped = new Pixmap( width, height, image.getFormat() );
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                flipped.setColor( image.getPixel( x, y ) );
                flipped.drawPixel( x, height - y - 1 );
            }
        }

        PixmapIO.writePNG( file, flipped );
        flipped.dispose();
    }

}
