package com.github.mdsimmo.pixxit.gui.views;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.gui.Cursor;

/**
 * For private use by the DrawingView. This class does the stuff relating to translating screen
 * co-ords into tool co-ords and telling the tools to do something.
 */
class ToolControl extends InputListener {

    private final Drawing drawing;
    private final OrthographicCamera camera;
    private final DrawingView view;

    private int MAX_TOUCHES = 16;
    // Android can have more than touch, thus, each "finger" gets one slot in this array
    // the tool that the current touch is using
    private Tool[] tools = new Tool[MAX_TOUCHES];
    // info about where the finger was previously
    private Cursor[] prev = new Cursor[MAX_TOUCHES];

    public ToolControl( DrawingView view, Drawing drawing, OrthographicCamera camera ) {
        this.drawing = drawing;
        this.camera = camera;
        this.view = view;
    }

    @Override
    public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
        if ( button == Input.Buttons.MIDDLE )
            return false; // middle button is for zooming

        // ignore touches when too many fingers are down
        if ( pointer >= MAX_TOUCHES )
            return false;
        // desktop sometimes generates a new touch when the old touch is still active. Ignore the new one
        if ( prev[pointer] != null )
            return false;

        // update the current tool on touch down. This avoids a tool being used half way though an action
        tools[pointer] = drawing.tool();

        tools[pointer].started( drawing, getCursor( x, y, pointer, button ) );
        return true;
    }

    @Override
    public void touchDragged( InputEvent event, float x, float y, int pointer ) {
        tools[pointer].activate( drawing, getCursor( x, y, pointer, -1 ) );
    }

    @Override
    public void touchUp( InputEvent event, float x, float y, int pointer, int button ) {
        tools[pointer].stopped( drawing, getCursor( x, y, pointer, -1 ) );
        // release the tool and the pointer
        tools[pointer] = null;
        prev[pointer] = null;
    }

    private Cursor getCursor( float x, float y, int pointer, int button ) {
        x -= camera.position.x + view.getWidth() /2;
        y -= camera.position.y + view.getHeight()/2;
        x /= camera.zoom;
        y /= camera.zoom;
        x += drawing.width()/2;
        y += drawing.height()/2;


        Cursor prev = this.prev[pointer];
        if ( prev == null ) {
            // first touch. Generate a fake previous touch
            Cursor.Button b;
            switch ( button ) {
                case Input.Buttons.LEFT:
                    b = Cursor.Button.LEFT;
                    break;
                case Input.Buttons.MIDDLE:
                    b = Cursor.Button.MIDDLE;
                    break;
                case Input.Buttons.RIGHT:
                    b = Cursor.Button.RIGHT;
                    break;
                default:
                    throw new IllegalArgumentException( "What is button " + button + "?" );
            }
            prev = new Cursor( (int)x, (int)y, (int)x, (int)y, 0, 0, pointer, b );
        }

        Cursor newCursor = new Cursor(
                (int)x, (int)y, prev.xStart(), prev.yStart(),
                (int)(x-prev.x()), (int)(y-prev.y()), pointer, prev.button() );

        this.prev[pointer] = newCursor;

        return newCursor;
    }
}
