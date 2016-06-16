package com.github.mdsimmo.pixxit;

import com.github.mdsimmo.pixxit.utils.KeyCode;

/**
 * An Operation is a thing that can do something to a canvas but does not need to be controlled by
 * the mouse
 */
public interface Operation {

    /**
     * The key code that will activate the tool (or null is there is none)
     */
    KeyCode keyCode();

    /**
     * Applies this operation to the drawing
     */
    void apply( Drawing drawing );

}
