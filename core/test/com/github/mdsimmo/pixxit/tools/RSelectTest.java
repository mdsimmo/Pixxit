package com.github.mdsimmo.pixxit.tools;

import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.gui.Cursor;
import org.junit.Test;

import static org.junit.Assert.*;

public class RSelectTest {

    @Test
    public void testSelectRegion() {
        RSelect tool = new RSelect();
        Drawing drawing = new Drawing( 10, 10 );
        tool.started( drawing, new Cursor( 2, 3, 2, 3, 0, 0, 0, Cursor.Button.LEFT ) );
        tool.stopped( drawing, new Cursor( 5, 7, 2, 3, 0, 0, 0, Cursor.Button.LEFT ));

        assertTrue( drawing.selection().isSelected( 3, 4 ) );
        assertTrue( drawing.selection().isSelected( 2, 3 ) );
        assertTrue( drawing.selection().isSelected( 4, 6 ) );

        assertFalse( drawing.selection().isSelected( 5, 7 ) );
    }

}