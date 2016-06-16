package com.github.mdsimmo.pixxit.utils;

/**
 * Represents a point in 2D space with integer components. The point can be mutated
 */
public class Point {

    public int x, y;

    public Point( int x, int y ) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this( 0, 0 );
    }

    public Point( Point p ) {
        this( p.x, p.y );
    }

    public Point set( int x, int y ) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Point set( Point p ) {
        return set( p.x, p.y );
    }

    public Point add( int x, int y ) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point add( Point p ) {
        return add( p.x, p.y );
    }

    public Point sub( int x, int y ) {
        return add( -x, -y );
    }

    public Point sub( Point p ) {
        return sub( p.x, p.y );
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
