package com.github.mdsimmo.pixxit.gui.views;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Tool;
import com.github.mdsimmo.pixxit.ToolBox;
import com.github.mdsimmo.pixxit.gui.FlowLayout;
import com.github.mdsimmo.pixxit.gui.View;

import java.util.HashMap;

/**
 * Displays the toolbox to the user
 */
public class ToolBoxView extends View {

    private final Drawing drawing;
    private Tool current;
    private HashMap<Class<? extends Tool>, ToolButton> lookUp;

    public ToolBoxView( Skin skin, ToolBox toolBox, final Drawing drawing ) {
        super( skin );
        this.drawing = drawing;
        this.lookUp = new HashMap<>();

        // add the tool buttons
        FlowLayout layout = new FlowLayout();
        ButtonGroup<Button> buttons = new ButtonGroup<>();
        buttons.setMaxCheckCount( 1 );
        buttons.setMinCheckCount( 1 );
        for ( final Tool tool : toolBox ) {
            ToolButton button = new ToolButton( tool, skin );
            layout.addActor( button );
            buttons.add( button );
            lookUp.put( tool.getClass(), button );
        }

        layout.setFillParent( true );
        layout.setAlign( Align.top );
        layout.setPadding( 4 );

        addActor( layout );
    }

    @Override
    public void act( float delta ) {
        // user can change tools though hotkeys. Thus, we need to keep checking if the
        // correct tool button is pressed
        Tool active = drawing.tool();
        if ( !active.getClass().equals( current.getClass() ) ) {
            // recalculate what button is down
            ToolButton button = lookUp.get( active.getClass() );
            if ( button != null )
                button.setChecked( true );
        }
    }

    static ImageButton.ImageButtonStyle makeStyle( Tool tool, Skin skin ) {
        ImageButton.ImageButtonStyle style = skin.get( "toggle", ImageButton.ImageButtonStyle.class );
        style = new ImageButton.ImageButtonStyle( style );
        style.imageUp = skin.getDrawable( tool.name() + "-icon" );
        return style;
    }

    class ToolButton extends ImageButton {
        Tool tool;

        public ToolButton( Tool tool, Skin skin ) {
            super( makeStyle( tool, skin ) );
            this.tool = tool;
            addListener( new ChangeListener() {
                @Override
                public void changed( ChangeEvent event, Actor actor ) {
                    drawing.setTool( tool );
                    current = tool;
                }
            } );
        }

    }
}
