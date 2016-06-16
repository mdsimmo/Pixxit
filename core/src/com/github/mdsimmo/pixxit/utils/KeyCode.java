package com.github.mdsimmo.pixxit.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import java.util.Arrays;

import static com.badlogic.gdx.Input.Keys.*;

/**
 * A class that represents a Key stroke combination, for example, ctrl + shift + c
 */
public class KeyCode {

    /**
     * Creates a KeyCode that will be triggered when any of the passed KeyCodes are triggered
     */
    public static KeyCode any( KeyCode ... keys ) {
        return new KeyCode() {
            @Override
            public boolean isTriggered() {
                for (KeyCode key : keys )
                    if (key.isTriggered())
                        return true;
                return false;
            }
        };
    }

    private final int[] keys;

    public KeyCode( int ... keys ) {
        this.keys = Arrays.copyOf( keys, keys.length );
    }

    /**
     * Tests if the KeyCode was triggered in the previous frame
     */
    public boolean isTriggered() {
        boolean oneTriggered = false;
        for ( int key : keys ) {
            if (!Gdx.input.isKeyPressed( key ))
                return false;
            oneTriggered |= Gdx.input.isKeyJustPressed( key );
        }
        return oneTriggered && checkCorrectModifiers();
    }

    private boolean checkCorrectModifiers() {
        // note: we ignore the right alt/ctrl/shift keys (they are unused)
        boolean hasAlt = false;
        boolean hasShift = false;
        boolean hasCtrl = false;
        for (int key : keys ) {
            if ( key == CONTROL_LEFT )
                hasCtrl = true;
            if ( key == SHIFT_LEFT )
                hasShift = true;
            if ( key == ALT_LEFT )
                hasAlt = true;
        }

        if ( !hasShift ) {
            if ( Gdx.input.isKeyPressed( SHIFT_LEFT ) )
                return false;
        }
        if ( !hasAlt ) {
            if ( Gdx.input.isKeyPressed( ALT_LEFT ) )
                return false;
        }
        if ( !hasCtrl ) {
            if ( Gdx.input.isKeyPressed( CONTROL_LEFT ) )
                return false;
        }

        return true;
    }

}
