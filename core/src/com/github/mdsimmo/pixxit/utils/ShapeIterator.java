package com.github.mdsimmo.pixxit.utils;

import com.badlogic.gdx.math.MathUtils;
import com.github.mdsimmo.pixxit.Canvas;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class for iterating over common shapes.
 */
public class ShapeIterator {

    /**
     * Iterates through each pixel from one point to another point
     */
    public static Iterable<Point> line( int x1, int y1, int x2, int y2 ) {
        return () -> new Iterator<Point>() {
            final int dx = x2 - x1;
            final int dy = y2 - y1;

            final int iterations = (int)Math.sqrt( dx*dx + dy*dy ) + 1;
            int count = 0;
            final Point p = new Point( x1, y1 );

            @Override
            public boolean hasNext() {
                return count <= iterations;
            }

            @Override
            public Point next() {
                p.x = MathUtils.round( x1 + ((float)dx) * count / iterations );
                p.y = MathUtils.round( y1 + ((float)dy) * count / iterations );
                count++;
                return p;
            }
        };
    }

    /**
     * Iterates through an area using changes in the color of the canvas as the border. The temp
     * layer is used to store where the iterator has been. It must not be altered during iteration
     * or else results may be strange.
     */
    public static Iterable<Point> area( int x, int y, Canvas canvas, Canvas temp ) {
        Color replace = canvas.getColor( x, y );
        if ( replace == null )
            return new ArrayList<>();
        temp.clear();
        temp.drawCanvas( canvas, 0, 0 );
        Color fill = replace.equals( Color.BLACK ) ? Color.WHITE : Color.BLACK;
        ArrayList<Point> points = findPoints( temp, x, y, replace, fill );
        temp.clear();
        return points;
    }

    private static ArrayList<Point> findPoints( Canvas canvas, int x, int y, Color replace, Color fill ) {
        ArrayList<Point> queue = new ArrayList<>();
        ArrayList<Point> found = new ArrayList<>();
        queue.add( new Point( x, y ) );
        while ( !queue.isEmpty() ) {
            // take last element so other nodes do not need to be moved
            Point next = queue.remove( queue.size()-1 );
            find( canvas, next, replace, fill, queue, found );
        }
        return found;
    }
    private static void find( Canvas canvas, Point p, Color replace, Color fill, ArrayList<Point> queue, ArrayList<Point> found ) {
        if ( !replace.equals( canvas.getColor( p.x, p.y ) ) )
            return; // should not get filled
        canvas.draw( p.x, p.y, fill );
        found.add( p );
        queue.add( new Point( p.x+1, p.y ) );
        queue.add( new Point( p.x-1, p.y ) );
        queue.add( new Point( p.x, p.y+1 ) );
        queue.add( new Point( p.x, p.y-1 ) );
    }
}