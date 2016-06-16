package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.github.mdsimmo.pixxit.utils.Callback;
import com.github.mdsimmo.pixxit.utils.Color;

/**
 * A widget that lets the user select a Color from a HSV region. Any changes to the selection will
 * be updated to the callback
 */
public class HSVSelect extends WidgetGroup {

    private static final int SIZE = 128;
    private static final int PADDING = 8;

    private Pixmap selectPix;
    private Texture selectTex;

    private Pixmap sliderPix;
    private Texture sliderTex;

    private Image crossHair;
    private Slider slider;

    private Color current;

    public HSVSelect( Color init, final Callback<Color> callback, Skin skin) {
        this.current = init;

        // create the selection area
        selectPix = new Pixmap( SIZE, SIZE, Pixmap.Format.RGB888 );
        fillSelect( init.hue, selectPix );
        selectTex = new Texture( selectPix );
        Image image = new Image( new TextureRegionDrawable( new TextureRegion( selectTex ) ) );
        addActor( image );

        // create the cross hair
        crossHair = new Image( skin.getDrawable( "cross-hair" ) );
        crossHair.setTouchable( Touchable.disabled );
        addActor( crossHair );

        // create the slider area
        sliderPix = new Pixmap( 16, SIZE, Pixmap.Format.RGBA8888 );
        fillSlider( sliderPix );
        sliderTex = new Texture( sliderPix );
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle( skin.get( "color-select", Slider.SliderStyle.class ) );
        sliderStyle.background = new TextureRegionDrawable( new TextureRegion( sliderTex ) );
        slider = new Slider( 0, 360, 1, true, sliderStyle );
        slider.setSize( 16, SIZE );
        addActor( slider );
        slider.setPosition( SIZE + PADDING, 0 );

        // Create the listeners
        slider.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                int h = (int)(slider.getValue());
                int s = current.saturation;
                int v = current.value;
                current = Color.hsv( h, s, v );
                updateSelectArea();
                callback.notify( current );
            }
        } );
        image.addListener( new ClickListener() {
            @Override
            public boolean touchDown( InputEvent event, float x, float y, int pointer, int button ) {
                setPosition( x, y );
                return true;
            }

            @Override
            public void touchDragged( InputEvent event, float x, float y, int pointer ) {
                setPosition( x, y );
            }

            private void setPosition( float x, float y ) {
                x = MathUtils.clamp( x, 0, SIZE );
                y = MathUtils.clamp( y, 0, SIZE );
                int h = current.hue;
                int v = (int)(x/SIZE * 100);
                int s = (int)(y/SIZE * 100);
                current = Color.hsv( h, s, v );
                crossHair.setPosition( (int)(x-crossHair.getWidth()/2), (int)(y-crossHair.getHeight()/2) );
                callback.notify( current );
            }
        });

        // update the positions/colors of everything
        setSelectedColor( init );
    }

    public void setSelectedColor( Color c ) {
        current = c;
        updateSelectArea();
        updateSlider();
    }

    private void updateSelectArea() {
        fillSelect( current.hue, selectPix );
        selectTex.draw( selectPix, 0, 0 );
        crossHair.setPosition(
                (int)(current.value*SIZE/100f-crossHair.getWidth()/2),
                (int)(current.saturation*SIZE/100f-crossHair.getHeight()/2) );
    }

    private void updateSlider() {
        slider.setValue( current.hue );
    }

    private static void fillSelect( int hue, Pixmap image) {
        float h = hue / 360f;
        com.badlogic.gdx.graphics.Color c = new com.badlogic.gdx.graphics.Color( 0, 0, 0, 0 );
        for ( int x = 0; x < image.getWidth(); x++ ) {
            for ( int y = 0; y < image.getHeight(); y++ ) {
                float v = ((float)x)/image.getWidth();
                float s = 1-((float)y)/image.getHeight();
                int rgb = java.awt.Color.HSBtoRGB( h, s, v );
                com.badlogic.gdx.graphics.Color.argb8888ToColor( c, rgb );
                image.setColor( c );
                image.drawPixel( x, y );
            }
        }
    }

    private static void fillSlider( Pixmap image ) {
        float s = 1, v = 1;
        com.badlogic.gdx.graphics.Color c = new com.badlogic.gdx.graphics.Color();
        for ( int y = 0; y < image.getHeight(); y++ ) {
            int rgb = java.awt.Color.HSBtoRGB( -((float)y)/image.getHeight(), s, v );
            com.badlogic.gdx.graphics.Color.argb8888ToColor( c, rgb );
            image.setColor( c );
            image.drawLine( 0, y, image.getWidth()-1, y );
        }
    }

    @Override
    public float getPrefHeight() {
        return SIZE;
    }

    @Override
    public float getPrefWidth() {
        return SIZE + PADDING + 16;
    }

    @Override
    public boolean remove() {
        selectPix.dispose();
        selectTex.dispose();
        sliderPix.dispose();
        sliderTex.dispose();
        return super.remove();
    }
}
