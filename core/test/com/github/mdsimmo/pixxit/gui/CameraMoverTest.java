package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.github.mdsimmo.pixxit.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

// Disabled because the design has changed
public class CameraMoverTest {

//
//    static {
//        TestUtils.initGDX();
//    }
//
//    @Test
//    public void testDrag() {
//        OrthographicCamera camera = new OrthographicCamera( 200, 100 );
//        camera.position.set( 10, 20, 0 );
//        camera.update();
//        CameraMover mover = new CameraMover( camera );
//
//        InputEvent e = new InputEvent();
//        boolean received = mover.touchDown( e, 20, 60, 0, Input.Buttons.MIDDLE );
//        mover.touchDragged( e, 25, 62, 0 );
//
//        assertTrue( received );
//
//        // I don't know where these co-ordinates came from
//        // I got them by making it work and then recording the values it gave.
//        // Any changes should still get these same values
//        assertEquals( 5, camera.position.x, 0.01f );
//        assertEquals( 18, camera.position.y, 0.01f );
//
//        mover.touchDragged( e, 30, 70, 0 );
//
//        assertEquals( -5, camera.position.x, 0.01f );
//        assertEquals( 8, camera.position.y, 0.01f );
//
//        mover.touchUp( e, 26, 67, 0, Input.Buttons.RIGHT );
//
//        assertEquals( -11, camera.position.x, 0.01f );
//        assertEquals( 1, camera.position.y, 0.01f );
//    }
//
//    @Test
//    public void testNotDragged() {
//        OrthographicCamera camera = new OrthographicCamera( 200, 100 );
//        camera.position.set( 0, 0, 0 );
//        CameraMover mover = new CameraMover( camera );
//
//        InputEvent e = new InputEvent();
//        // left button is wrong so this should get ignored
//        boolean received = mover.touchDown( e, 20, 60, 0, Input.Buttons.LEFT );
//
//        assertFalse( received );
//    }
//
//    @Test
//    public void testZoom() {
//        OrthographicCamera camera = new OrthographicCamera( 200, 100 );
//        camera.zoom = 5;
//        CameraMover mover = new CameraMover( camera );
//
//        mover.scrolled( null, 20, 50, 10 );
//    }

}