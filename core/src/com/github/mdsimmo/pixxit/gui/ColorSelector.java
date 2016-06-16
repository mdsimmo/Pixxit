package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.github.mdsimmo.pixxit.utils.Callback;
import com.github.mdsimmo.pixxit.utils.Color;

/**
 * A popup window that lets the user select a color. When the user presses the okay button, the
 * selected color is returned in the callback.
 */
public class ColorSelector extends Window {

    private TabbedView tabs;
    private Callback<Color> callback;
    private Color selected;

    public ColorSelector( Skin skin, Callback<Color> callback, final Color init ) {
        super( "Color select", skin );
        this.selected = init;
        this.callback = callback;

        tabs = new TabbedView();
        add( tabs ).top().expand().fillX();

        Callback<Color> colorChanged = c -> selected = c;

        tabs.addTab( new IconButton( skin, "hsv-icon" ), new HSVSelect( init, colorChanged, skin ) );
        tabs.addTab( new IconButton( skin, "rgb-icon" ), new Label( "Unimplemented", skin ) );
        tabs.addTab( new IconButton( skin, "palate-icon" ), new Label( "Unimplemented", skin ) );

        row();

        // add the alpha slider
        Slider alphaSelect = new Slider( 0, 255, 1, false, skin );
        alphaSelect.setSize( 128, 16 );
        alphaSelect.setValue( init.ai );
        alphaSelect.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                int a = (int)alphaSelect.getValue();
                Color c = selected;
                selected = Color.hsva( c.hue, c.saturation, c.value, a );
            }
        } );
        add( alphaSelect );

        row();

        Button apply = new TextButton( "Apply", skin );
        apply.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                exit( true );
            }
        });
        Button cancel = new TextButton( "Cancel", skin );
        cancel.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                exit( false );
            }
        } );
        add( new FlowLayout().add(apply, cancel) ).expandX().fillX();

        this.pack();

        // set the color selector window to be where the cursor is
        setPosition( Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY() );
    }

    private void exit( boolean saved ) {
        if ( saved )
            callback.notify( selected );
        remove();
    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {
        super.draw( batch, parentAlpha );
        tabs.draw( batch, parentAlpha );
    }


}
