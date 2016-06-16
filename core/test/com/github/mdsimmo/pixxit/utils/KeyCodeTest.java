package com.github.mdsimmo.pixxit.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KeyCodeTest {

    @Test
    public void testSingleButton() {
        Gdx.input = mock( Input.class );
        when( Gdx.input.isKeyPressed( Input.Keys.C ) ).thenReturn( true );
        when( Gdx.input.isKeyJustPressed( Input.Keys.C ) ).thenReturn( true );
        KeyCode key = new KeyCode( Input.Keys.C );

        assertTrue( key.isTriggered() );
    }

    @Test
    public void testControlButton() {
        Gdx.input = mock( Input.class );
        when( Gdx.input.isKeyPressed( Input.Keys.C ) ).thenReturn( true );
        when( Gdx.input.isKeyPressed( Input.Keys.ALT_LEFT ) ).thenReturn( true );
        when( Gdx.input.isKeyJustPressed( Input.Keys.C ) ).thenReturn( true );
        KeyCode key = new KeyCode( Input.Keys.C, Input.Keys.ALT_LEFT );

        assertTrue( key.isTriggered() );
    }

    @Test
    public void testControlButtonMissmatch() {
        Gdx.input = mock( Input.class );
        when( Gdx.input.isKeyPressed( Input.Keys.C ) ).thenReturn( true );
        when( Gdx.input.isKeyPressed( Input.Keys.ALT_LEFT ) ).thenReturn( true );
        when( Gdx.input.isKeyPressed( Input.Keys.SHIFT_LEFT ) ).thenReturn( true );
        when( Gdx.input.isKeyJustPressed( Input.Keys.C ) ).thenReturn( true );
        KeyCode key = new KeyCode( Input.Keys.C, Input.Keys.ALT_LEFT );

        assertFalse( key.isTriggered() );
    }

}