package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.github.mdsimmo.pixxit.Operation;

import java.util.*;

public class View extends WidgetGroup {

    private ViewStyle style;

    public View() {
        addListener( new ClickListener() {
            @Override
            public void exit( InputEvent event, float x, float y, int pointer, Actor toActor ) {
            }

            @Override
            public void enter( InputEvent event, float x, float y, int pointer, Actor fromActor ) {
                getStage().setScrollFocus( View.this );
            }
        });
    }

    public View( ViewStyle style ) {
        this();
        setStyle( style );
    }

    public View( Skin skin, String styleName ) {
        this(skin.get( styleName, ViewStyle.class ));
    }

    public View( Skin skin ) {
        this(skin.get( ViewStyle.class ));
    }

    public ViewStyle getStyle() {
        return style;
    }

    public void setStyle( ViewStyle style ) {
        this.style = style;
    }

    @Override
    public void draw( Batch batch, float parentAlpha ) {
        if (style.background != null)
            style.background.draw( batch, getX(), getY(), getWidth(), getHeight() );

        customDraw( batch );
        super.draw( batch, parentAlpha );
        batch.setColor( getColor() );

        if (style != null && style.overlay != null)
            style.overlay.draw( batch, getX(), getY(), getWidth(), getHeight() );
        if (style != null && style.corner != null) {
            Drawable c = style.corner;
            c.draw( batch,
                    getX() + getWidth() - c.getMinWidth(), getY() + getHeight() - c.getMinHeight(),
                    c.getMinWidth(), c.getMinHeight() );
        }
    }

    protected void customDraw( Batch batch ) {
    }

    /**
     * The styling for a view.
     */
    public static class ViewStyle {
        /**
         * What to draw over the top. Should be mostly transparent
         */
        public Drawable overlay;

        /**
         * The corner grabber thingy
         */
        public Drawable corner;

        public Drawable background;

        public ViewStyle() {
        }

        public ViewStyle( Drawable overlay, Drawable corner, Drawable background ) {
            this.overlay = overlay;
            this.corner = corner;
            this.background = background;
        }

        public ViewStyle( ViewStyle style ) {
            this.overlay = style.overlay;
            this.corner = style.corner;
            this.background = style.background;
        }
    }

}
