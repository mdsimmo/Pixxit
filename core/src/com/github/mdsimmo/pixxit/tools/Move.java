package com.github.mdsimmo.pixxit.tools;

import com.badlogic.gdx.Input;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;

import java.util.HashMap;

public class Move implements Tool {

    /**
     * Class to store data relevant for the moving process
     */
    private class MovingData {
        private Canvas original;
        private Drawing drawing;
        private Canvas active;
        private int dx, dy;
    }

    // map of pointer index to movement data
    private HashMap<Integer, MovingData> datas = new HashMap<>();

    @Override
    public void started( Drawing drawing, Cursor cursor ) {
        // ignore multiple touches
        if (datas.get( cursor.pointer() ) != null )
            return;

        // store initial information
        MovingData data = new MovingData();
        data.active = drawing.activeLayer();
        data.original = new Canvas( data.active.width(), data.active.height() );
        data.original.drawCanvas( data.active, 0, 0 );
        data.drawing = drawing;
        datas.put( cursor.pointer(), data );

        // move the current layer to the temporary layer
        data.active.clear();
        drawing.tempLayer().clear();
        drawing.tempLayer().drawCanvas( data.original, 0, 0 );
    }

    @Override
    public void activate( Drawing drawing, Cursor cursor ) {
        updatePosition( drawing, cursor );
    }

    @Override
    public void stopped( Drawing drawing, Cursor cursor ) {
        MovingData data = updatePosition( drawing, cursor );

        // clean up
        drawing.tempLayer().clear();
        data.active.drawCanvas( data.original, data.dx, data.dy );
        data.original.dispose();
        datas.remove( cursor.pointer() );
    }

    private MovingData updatePosition( Drawing drawing, Cursor cursor ) {
        MovingData data = datas.get( cursor.pointer() );
        data.dx = cursor.x() - cursor.xStart();
        data.dy = cursor.y() - cursor.yStart();
        drawing.tempLayer().clear();
        drawing.tempLayer().drawCanvas( data.original, data.dx, data.dy );
        return data;
    }

    @Override
    public String name() {
        return "move";
    }

    @Override
    public KeyCode keyCode() {
        return new KeyCode( Input.Keys.M );
    }
}
