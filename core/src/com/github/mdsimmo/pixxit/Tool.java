package com.github.mdsimmo.pixxit;

import com.github.mdsimmo.pixxit.gui.Cursor;
import com.github.mdsimmo.pixxit.utils.KeyCode;

/**
 * A tool is anything that the human can do stuff with
 */
public interface Tool {

    /**
     * Always called once when the human clicks using the tool. The x,y position may not be over the
     * image.
     */
    void started( Drawing drawing, Cursor cursor );

    /**
     * Called when the human moves their cursor while dragging. The x,y position may note be over
     * the image
     */
    void activate( Drawing drawing, Cursor cursor );

    /**
     * Called when the human releases their cursor. Always called once per use. The x,y position may
     * not be over the image
     */
    void stopped( Drawing drawing, Cursor cursor );

    /**
     * Gets the name of this tool
     * @return the tool's name
     */
    String name();

    /**
     * Gets the hot key for this tool
     * @return the tool's hot key. Null for no short cut key
     */
    default KeyCode keyCode() {
        return null;
    }

}
