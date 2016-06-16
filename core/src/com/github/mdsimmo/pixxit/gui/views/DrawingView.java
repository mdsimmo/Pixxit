package com.github.mdsimmo.pixxit.gui.views;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.gui.*;

/**
 * Displays the canvas to the user
 */
public class DrawingView extends View {

    private final Drawing drawing;
    private OrthographicCamera camera;
    private CameraMover mover;
    private ToolControl control;
    private Table header;
    private DrawingViewStyle style;

    public DrawingView( Skin skin, Drawing drawing ) {
        super( getStyle( skin ) );
        this.drawing = drawing;

        // note: we only use the camera as a holder for the position and zoom values
        camera = new OrthographicCamera( 1, 1 );
        camera.lookAt( 0, 0, 0 );
        mover = new CameraMover( camera );
        control = new ToolControl( this, drawing, camera );

        addListener( mover );
        addListener( control );

        header = new Table();
        header.align( Align.bottom );
        header.setFillParent( true );

        // primary color
        ColorSwatch primarySwatch = new ColorSwatch( drawing::primaryColor, 16 );
        primarySwatch.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y ) {
                getStage().addActor( new ColorSelector( skin, drawing::setPrimaryColor, drawing.primaryColor() ) );
            }
        });
        header.add( primarySwatch ).padLeft( 8 );

        // secondary color
        ColorSwatch secondarySwatch = new ColorSwatch( drawing::secondaryColor, 16 );
        secondarySwatch.addListener( new ClickListener() {
            @Override
            public void clicked( InputEvent event, float x, float y ) {
                getStage().addActor( new ColorSelector( skin, drawing::setSecondaryColor, drawing.secondaryColor() ) );
            }
        });
        header.add( secondarySwatch ).padLeft( 8 );

        // seperate left and right buttons
        header.add().expandX();

        Button button = new IconButton( skin, "zoom-reset");
        button.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                camera.zoom = 1;
                camera.position.set(0, 0, 0);
            }
        } );
        header.add( button );

        button = new IconButton( skin, "zoom-in");
        button.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                camera.zoom *= 1.5f;
            }
        } );
        header.add( button );

        button = new IconButton( skin, "zoom-out");
        button.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                camera.zoom /= 1.5f;
            }
        } );
        header.add( button );

        View view = new View( skin );
        view.addActor( header );
        Table table = new Table();
        table.setFillParent( true );
        table.align( Align.bottom );
        table.add( view ).fillX().expandX().height( 20 );
        addActor( table );
    }

    // LibGDX doesn't seem to let me place a TiledDrawable in a json file so I have to hack it in here
    private static DrawingViewStyle getStyle( Skin skin ) {
        DrawingViewStyle style = skin.get( DrawingViewStyle.class );
        style.transparent = skin.getTiledDrawable( "drawing-transparent" );
        return style;
    }

    @Override
    protected void customDraw( Batch batch ) {
        float x = getX() + getWidth()/2 + camera.position.x - (drawing.width()/2) * camera.zoom;
        float y = getY() + getHeight()/2 + camera.position.y - (drawing.height()/2) * camera.zoom;
        style.transparent.draw( batch, x, y, drawing.width() * camera.zoom, drawing.height() * camera.zoom );
        drawing.render( batch, x, y, camera.zoom );
    }

    public void setStyle( ViewStyle style ) {
        if (style instanceof DrawingViewStyle )
            this.style = (DrawingViewStyle)style;
        else
            throw new IllegalArgumentException( "Style must be a drawing view style" );
        super.setStyle(style);
    }

    @Override
    public DrawingViewStyle getStyle() {
        return style;
    }

    public static class DrawingViewStyle extends ViewStyle {
        public Drawable transparent;

        public DrawingViewStyle() {
        }

    }
}
