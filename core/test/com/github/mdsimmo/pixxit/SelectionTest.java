package com.github.mdsimmo.pixxit;

import org.junit.Test;

import static org.junit.Assert.*;

public class SelectionTest {

    @Test
    public void testSetSelected() {
        Selection selection = new Selection( 5, 3 );
        selection.setSelected( 4, 2, true );

        assertTrue( selection.isSelected( 4, 2 ) );

        // everywhere else should not be selected
        assertFalse( selection.isSelected( 3, 2 ) );
    }

    @Test
    public void testSetRectangleSelect() {
        Selection selection = new Selection( 5, 7 );
        selection.setRectangle( 2, 4, 1, 2, true );

        for ( int i = 0; i < 5; i++ ) {
            for ( int j = 0; j < 6; j++ ) {
                if ( i >= 2 && i < 2+1 && j >= 4 && j < 6+2 )
                    assertTrue( selection.isSelected( i, j ) );
                else
                    assertFalse( selection.isSelected( i, j ) );
            }
        }
    }

    @Test
    public void setRectangleBackwards() {
        Selection selection = new Selection( 5, 7 );
        selection.setRectangle( 3, 6, -2, -3, true );

        boolean fail = false;
        for ( int i = 0; i < 5; i++ ) {
            for ( int j = 0; j < 7; j++ ) {
                if ( i >= 1 && i <= 2  && j >= 3 && j <= 5 ) {
                    if ( !selection.isSelected( i, j ) ) {
                        System.out.println( i + "-" + j );
                        fail = true;
                    }
                } else {
                    if ( selection.isSelected( i, j ) ) {
                        System.out.println( i + " " + j );
                        fail = true;
                    }
                }
            }
        }
        if (fail) {
            throw new AssertionError( "Wrong values were set (see console output)" );
        }
    }

}