package com.github.mdsimmo.pixxit.tools;

import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;

/**
 * For simple tools that just want to provide some sort of stroke
 */
public abstract class BrushTool implements Tool {

    @Override
    public void started( Drawing drawing, Cursor cursor ) {
        draw( drawing, cursor );
    }

    @Override
    public void activate( Drawing drawing, Cursor cursor ) {
        draw( drawing, cursor );
    }

    @Override
    public void stopped( Drawing drawing, Cursor cursor ) {
        draw( drawing, cursor );
    }

    private void draw( Drawing drawing, Cursor c ) {
        drawLine( c.x()-c.xDelta(), c.y()-c.yDelta(), c.x(), c.y(), drawing, c );
    }

    protected abstract void drawLine( int xFrom, int yFrom, int xTo, int yTo, Drawing drawing, Cursor c );
}
