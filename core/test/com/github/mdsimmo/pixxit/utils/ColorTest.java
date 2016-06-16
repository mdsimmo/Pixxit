package com.github.mdsimmo.pixxit.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColorTest {

    private static void assertEqualsHex( int expected, int actual ) {
        try {
            assertEquals( expected, actual );
        } catch ( AssertionError e ) {
            throw new AssertionError(
                    "Expected:<" + Integer.toHexString( expected ) + ">" +
                            " but was:<" + Integer.toHexString( actual ) + ">" );
        }
    }

    @Test
    public void initFloat() {
        Color c = Color.f( 0, 1, 0.5f, 0.75f );

        // important these two are precise
        assertEquals( 0, c.ri );
        assertEquals( 255, c.gi );

        // don't really care what these are
        assertEquals( 127, c.bi );
        assertEquals( 191, c.ai );
    }

    @Test
    public void initInt() {
        Color c = Color.i( 0, 255, 128, 64 );

        assertEquals( 0, c.rf, 0 );
        assertEquals( 1, c.gf, 0 );
        assertEquals( 0.5, c.bf, 0.05 );
        assertEquals( 0.25, c.af, 0.05 );
    }

    @Test
    public void fromRGBA() {
        Color c = Color.rgba( 0xFF008040 );

        assertEquals( 255, c.ri );
        assertEquals( 0, c.gi );
        assertEquals( 128, c.bi );
        assertEquals( 64, c.ai );
    }

    @Test
    public void fromABGR() {
        Color c = Color.abgr( 0x12345678 );

        assertEqualsHex( c.toABGR(), 0x12345678 );
    }

    @Test
    public void toGdxColor() {
        Color c = Color.rgba( 0xFF008040 );

        com.badlogic.gdx.graphics.Color result = c.toGdxColor();

        assertEquals( c.toABGR(), result.toIntBits() );
    }

    @Test
    public void toABGR() {
        Color c = Color.rgba( 0x12345678 );

        assertEqualsHex( 0x78563412, c.toABGR() );
    }

    @Test
    public void toRBGA() {
        Color c = Color.rgba( 0x12345678 );

        assertEqualsHex( 0x12345678, c.toRGBA() );
    }

    @Test
    public void fromARGB() {
        Color c = Color.argb( 0x12345678 );

        assertEqualsHex( 0x34567812, c.toRGBA() );
    }

    @Test
    public void toARGB() {
        Color c = Color.rgba( 0x12345678 );

        assertEqualsHex( 0x78123456, c.toARGB() );
    }

    @Test
    public void fromHSVA() {
        Color c = Color.hsva( 200, 40, 80, 200 );

        // check correct transmission of values
        assertEquals( 200, c.hue, 2 );
        assertEquals( 40, c.saturation, 2 );
        assertEquals( 80, c.value, 2 );
        assertEquals( 200, c.ai, 2 );

        // check hsv to rgb conversion
        int argb = java.awt.Color.HSBtoRGB( 200f/360, 40f/100, 80f/100 );
        Color expected = Color.argb( argb );
        assertEquals( expected.ri, c.ri );
        assertEquals( expected.gi, c.gi );
        assertEquals( expected.bi, c.bi );
    }

    @Test
    public void testPreserveHSV() {
        // this test is tricky because the color is black, thus, if the color is first converted
        // to rgb, then the hue and saturation values are lost
        Color hsv = Color.hsv( 200, 70, 0 );

        assertEquals( hsv.hue, 200 );
        assertEquals( hsv.saturation, 70 );
        assertEquals( hsv.value, 0 );
    }

    @Test
    public void equals() {
        Color a = Color.rgba( 0x12345678 );
        Color b = Color.rgba( 0x12345678 );

        assertEquals( a, b );
        assertEquals( a.hashCode(), b.hashCode() );
    }

    @Test
    public void string() {
        Color c = Color.rgba( 0x12345678 );

        assertEquals( "12345678", c.toString() );
    }


}