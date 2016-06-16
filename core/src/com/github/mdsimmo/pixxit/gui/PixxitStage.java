package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * A stage is from LibGDX; it is used to position/render/update "Actors" on the screen
 */
public class PixxitStage extends Stage {
    private int xOffset = 0, yOffset = -3;

    public PixxitStage() {
        super();
    }

    public PixxitStage( Viewport viewport ) {
        super( viewport );
    }

    public PixxitStage( Viewport viewport, Batch batch ) {
        super( viewport, batch );
    }

    // LibGDX appears to have the mouse slightly offset from its real position (bug maybe?).
    // These methods hackilly fix that issue
    @Override
    public boolean touchDown( int screenX, int screenY, int pointer, int button ) {
        return super.touchDown( screenX + xOffset, screenY + yOffset, pointer, button );
    }
    @Override
    public boolean touchDragged( int screenX, int screenY, int pointer ) {
        return super.touchDragged( screenX + xOffset, screenY + yOffset, pointer );
    }
    @Override
    public boolean touchUp( int screenX, int screenY, int pointer, int button ) {
        return super.touchUp( screenX, screenY + yOffset, pointer, button );
    }
    @Override
    public boolean mouseMoved( int screenX, int screenY ) {
        return super.mouseMoved( screenX, screenY + yOffset );
    }
}
