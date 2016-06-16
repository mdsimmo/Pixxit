package com.github.mdsimmo.pixxit.utils;

import java.util.Objects;

public final class Color {

    public static final Color WHITE  = Color.i( 255, 255, 255, 255 );
    public static final Color BLACK  = Color.i(   0,   0,   0, 255 );
    public static final Color RED    = Color.i( 255,   0,   0, 255 );
    public static final Color GREEN  = Color.i(   0, 255,   0, 255 );
    public static final Color BLUE   = Color.i(   0,   0, 255, 255 );
    public static final Color YELLOW = Color.i( 255, 255,   0, 255 );
    public static final Color CYAN   = Color.i(   0, 255, 255, 255 );
    public static final Color MAGENTA= Color.i( 255,   0, 255, 255 );
    public static final Color PURPLE = Color.i( 128,   0, 128, 255 );
    public static final Color PINK   = Color.i( 250, 175, 190, 255 );
    public static final Color ORANGE = Color.i( 255, 165,   0, 255 );
    public static final Color GRAY   = Color.i( 100, 100, 100, 255 );
    public static final Color TRANSPARENT = Color.i( 0, 0, 0, 0 );

    /**
     * Creates a color with components using floating points. Values should be between 0 and 1
     */
    public static Color f( float r, float g, float b, float a ) {
        return new Color( r, g, b, a );
    }

    /**
     * Creates a color with integer components. Values should be between 0 and 255
     */
    public static Color i( int r, int g, int b, int a ) {
        return new Color( r, g, b, a );
    }

    public static Color rgba( int rgba ) {
        return new Color(
                (rgba & 0xFF000000 ) >>> 6*4,
                (rgba & 0x00FF0000 ) >>> 4*4,
                (rgba & 0x0000FF00 ) >>> 2*4,
                (rgba & 0x000000FF )
        );
    }

    public static Color argb( int argb ) {
        return new Color(
                (argb & 0x00FF0000 ) >>> 4*4,
                (argb & 0x0000FF00 ) >>> 2*4,
                (argb & 0x000000FF ),
                (argb & 0xFF000000 ) >>> 6*4
        );
    }

    public static Color abgr( int abgr ) {
        return new Color(
                (abgr & 0x000000FF ),
                (abgr & 0x0000FF00 ) >>> 2*4,
                (abgr & 0x00FF0000 ) >>> 4*4,
                (abgr & 0xFF000000 ) >>> 6*4
        );
    }

    public static Color gdx( com.badlogic.gdx.graphics.Color c) {
        return new Color( c.r, c.g, c.b, c.a );
    }

    public static Color hsv( int h, int s, int v ) {
        return hsva( h, s, v, 255 );
    }

    public static Color hsva( int h, int s, int v, int a ) {
        return new Color( h, s, v, a, true );
    }

    /**
     * Floating point component of the color. Values should be between 0 and 1.
     */
    public final float rf, gf, bf, af;
    /**
     * Integer components of the color. Values should be between 0 and 255
     */
    public final int ri, gi, bi, ai;

    /**
     * The hue of the color. The angle is between 0 and 360
     */
    public final int hue;
    /**
     * The saturation. A value between 0 and 100
     */
    public final int saturation;
    /**
     * The value. Between 0 and 100
     */
    public final int value;

    private Color( float r, float g, float b, float a ) {
        this.rf = r;
        this.gf = g;
        this.bf = b;
        this.af = a;

        this.ri = (int)(r*255);
        this.gi = (int)(g*255);
        this.bi = (int)(b*255);
        this.ai = (int)(a*255);

        float[] hsv = java.awt.Color.RGBtoHSB( ri, gi, bi, null );
        this.hue = (int)(hsv[0]*360);
        this.saturation = (int)(hsv[1]*100);
        this.value = (int)(hsv[2]*100);
    }

    private Color( int r, int g, int b, int a ) {
        this.ri = r;
        this.gi = g;
        this.bi = b;
        this.ai = a;

        this.rf = r/255f;
        this.gf = g/255f;
        this.bf = b/255f;
        this.af = a/255f;

        float[] hsv = java.awt.Color.RGBtoHSB( ri, gi, bi, null );
        this.hue = (int)(hsv[0]*360);
        this.saturation = (int)(hsv[1]*100);
        this.value = (int)(hsv[2]*100);
    }

    public Color( int h, int s, int v, int a, boolean hsv ) {
        this.hue = h;
        this.saturation = s;
        this.value = v;

        int rgb = java.awt.Color.HSBtoRGB( h/360f, s/100f, v/100f );
        this.ri = (rgb & 0x00FF0000) >> 4*4;
        this.gi = (rgb & 0x0000FF00) >> 2*4;
        this.bi = (rgb & 0x000000FF) >> 0*4;
        this.ai = a;

        this.rf = ri/255f;
        this.gf = gi/255f;
        this.bf = bi/255f;
        this.af = ai/255f;
    }

    public com.badlogic.gdx.graphics.Color toGdxColor() {
        return toGdxColor( new com.badlogic.gdx.graphics.Color() );
    }

    public com.badlogic.gdx.graphics.Color toGdxColor( com.badlogic.gdx.graphics.Color c ) {
        c.r = rf;
        c.g = gf;
        c.b = bf;
        c.a = af;
        return c;
    }

    public int toRGBA() {
        return (ri << 6*4) | (gi << 4*4) | (bi << 2*4) | ai;
    }

    public int toABGR() {
        return (ai << 6*4) | (bi << 4*4) | (gi << 2*4) | ri;
    }

    public int toARGB() {
        return (ai << 6*4) | (ri << 4*4) | (gi << 2*4) | bi;
    }

    @Override
    public String toString() {
        return Integer.toHexString( ri )
                + Integer.toHexString( gi )
                + Integer.toHexString( bi )
                + Integer.toHexString( ai );
    }

    @Override
    public boolean equals( Object obj ) {
        if (obj instanceof Color) {
            Color other = (Color)obj;
            return other.ri == this.ri
                    && other.gi == this.gi
                    && other.bi == this.bi
                    && other.ai == this.ai;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash( ri, gi, bi, ai );
    }
}
