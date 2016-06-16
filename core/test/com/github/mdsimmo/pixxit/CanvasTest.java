package com.github.mdsimmo.pixxit;

import com.github.mdsimmo.pixxit.utils.Color;
import org.junit.Test;

import static org.junit.Assert.*;

public class CanvasTest {

    static {
        TestUtils.initGDX();
    }

    @Test
    public void testInit() {
        Canvas canvas = new Canvas( 64, 32 );

        assertEquals( 64, canvas.width() );
        assertEquals( 32, canvas.height() );
    }

    @Test
    public void testPixelDraw() {
        Canvas canvas = new Canvas( 32, 64 );
        canvas.draw( 6,  2, Color.GREEN );
        canvas.draw( 9,  1, Color.PURPLE );
        canvas.draw( 3, 10, Color.ORANGE );

        assertEquals( Color.GREEN,  canvas.getColor( 6,  2 ));
        assertEquals( Color.PURPLE, canvas.getColor( 9,  1 ));
        assertEquals( Color.ORANGE, canvas.getColor( 3, 10 ));
    }
}