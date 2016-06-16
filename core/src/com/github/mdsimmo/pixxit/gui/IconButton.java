package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Primarily, this is just a shortcut class to create an ImageButton and attach an icon to it
 */
public class IconButton extends ImageButton {

    public interface Listener {
        void onClick();
    }

    public IconButton( Skin skin, String iconName ) {
        this( skin, iconName, null );
    }

    public IconButton( Skin skin, String iconName, Listener listener ) {
        super( skin.get( ImageButtonStyle.class ) );
        ImageButtonStyle style = new ImageButtonStyle( skin.get( ImageButtonStyle.class ) );
        style.imageUp = skin.getDrawable( iconName );
        setStyle( style );

        if ( listener != null )
            addListener( new ClickListener() {
                @Override
                public void clicked( InputEvent event, float x, float y ) {
                    listener.onClick();
                }
            });
    }

    public IconButton( ImageButtonStyle style ) {
        super( style );
    }
}
