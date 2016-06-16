package com.github.mdsimmo.pixxit.gui.views;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.github.mdsimmo.pixxit.Canvas;
import com.github.mdsimmo.pixxit.Drawing;
import com.github.mdsimmo.pixxit.Operation;
import com.github.mdsimmo.pixxit.gui.IconButton;
import com.github.mdsimmo.pixxit.gui.View;
import com.github.mdsimmo.pixxit.operations.DeleteLayer;

import java.util.ArrayList;
import java.util.List;

/**
 * A View to display the drawings Layers. The view also allows the user to move layers around
 */
public class LayerView extends View {

    private static final int PAD = 8;
    private static final int SIZE = 64;
    private LayerViewStyle style;

    private final Drawing drawing;
    private List<LayerWidget> children = new ArrayList<>();

    public LayerView( Skin skin, Drawing drawing ) {
        super( getStyle( skin ) );
        this.drawing = drawing;

        // layer views added in act()

        Table header = new Table();
        header.setFillParent( true );

        Operation delete = new DeleteLayer();
        header.add().expandX(); // force right alignment
        header.add( new IconButton( skin, "remove-icon", () -> delete.apply( drawing ) ) );
        header.add( new IconButton( skin, "add-icon", drawing::newLayer ) );

        View headerView = new View( skin );
        headerView.addActor( header );

        // add menu bar
        Table headerContainer = new Table();
        headerContainer.setFillParent( true );
        headerContainer.align( Align.bottom );
        headerContainer.add( headerView ).expandX().fillX().height( 20 );

        addActor( headerContainer );
    }

    // LibGDX doesn't seem to let me place a TiledDrawable in a json file so I have to hack it in here
    private static LayerViewStyle getStyle( Skin skin ) {
        LayerViewStyle style = skin.get( LayerViewStyle.class );
        style.transparent = skin.getTiledDrawable( "drawing-transparent" );
        return style;
    }

    @Override
    public void act( float delta ) {
        super.act( delta );

        // remove deleted layer widgets
        while (children.size() > drawing.layers().size()) {
            children.remove( children.size()-1 ).remove();
            invalidate();
        }

        // add new layer widgets
        for ( int i = children.size(); i < drawing.layers().size(); i++ ) {
            LayerWidget widget = new LayerWidget( i );
            addActor( widget );
            children.add( widget );
            invalidate();
        }
    }

    @Override
    public void layout() {
        float x = PAD;
        float y = getHeight() - (children.size()+1) * (PAD + SIZE);
        for ( LayerWidget widget : children ) {
            y += SIZE + PAD;
            widget.setPosition( x, y );
        }
    }

    @Override
    public void setStyle( ViewStyle style ) {
        if (style instanceof LayerViewStyle )
            this.style = (LayerViewStyle)style;
        else
            throw new IllegalArgumentException( "Must set a LayerViewStyle" );
        super.setStyle( style );
    }

    @Override
    public LayerViewStyle getStyle() {
        return style;
    }

    @Override
    public float getMinWidth() {
        return SIZE + PAD * 2;
    }

    private class LayerWidget extends WidgetGroup {

        public LayerWidget( int index ) {
            // Add the layer preview icon
            addActor( new Actor() {
                {
                    setPosition( 0, 0 );
                    setSize( SIZE, SIZE );
                    addListener( new ClickListener() {
                        @Override
                        public void clicked( InputEvent event, float x, float y ) {
                            drawing.setActiveLayer( index );
                        }
                    });
                }
                @Override
                public void draw( Batch batch, float parentAlpha ) {
                    // draw the outline
                    Drawable outline = drawing.activeLayer() == drawing.layers().get( index ) ? style.selected : style.outline;
                    outline.draw( batch, getX()-outline.getLeftWidth(), getY()-outline.getBottomHeight(),
                            SIZE+outline.getLeftWidth()+outline.getRightWidth(), SIZE+outline.getTopHeight()+outline.getBottomHeight() );

                    // draw the checkered background
                    style.transparent.draw( batch, getX(), getY(), SIZE, SIZE );

                    // draw the layer
                    float scale = (float)SIZE / drawing.width();
                    drawing.layers().get( index ).render( batch, getX(), getY(), scale );
                }
            });

            float arrowSize = style.down.getMinHeight() + style.up.getMinHeight() + PAD;
            float center = SIZE/2;

            // add the down arrow
            addActor( new ImageButton( style.down ) {{
                setPosition( SIZE + PAD, center - arrowSize/2 );
                addListener( new ChangeListener() {
                    @Override
                    public void changed( ChangeEvent event, Actor actor ) {
                        if ( index == 0 )
                            return;
                        // shift the layer down
                        Canvas layer = drawing.layers().get( index );
                        drawing.layers().remove( index );
                        drawing.layers().add( index - 1, layer );
                    }
                } );
            }} );

            // add the up arrow
            addActor( new ImageButton( style.up ) {{
                setPosition( SIZE + PAD, center + arrowSize/2 - getHeight() );
                addListener( new ChangeListener() {
                    @Override
                    public void changed( ChangeEvent event, Actor actor ) {
                        if (index + 1 == drawing.layers().size())
                            return;
                        // shift the layer up
                        Canvas layer = drawing.layers().get( index );
                        drawing.layers().remove( index );
                        drawing.layers().add( index + 1, layer );
                    }
                } );
            }} );

            setSize( SIZE, SIZE );
        }

        @Override
        public float getMinWidth() {
            return getWidth();
        }

        @Override
        public float getMinHeight() {
            return getHeight();
        }
    }

    public static class LayerViewStyle extends ViewStyle {
        public Drawable transparent;
        public Drawable outline;
        public Drawable selected;
        public Drawable down, up;

        public LayerViewStyle() {
        }

    }
}
