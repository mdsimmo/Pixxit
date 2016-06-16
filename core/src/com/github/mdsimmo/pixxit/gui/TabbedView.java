package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class TabbedView extends WidgetGroup {

    private Table tabsTable;
    private ButtonGroup<Button> buttons;
    private Container<Actor> contents;

    public TabbedView() {
        buttons = new ButtonGroup<>();
        buttons.setMaxCheckCount( 1 );
        buttons.setMaxCheckCount( 1 );
        tabsTable = new Table();
        tabsTable.align( Align.topLeft );
        contents = new Container<>();

        Table table = new Table();
        table.setFillParent( true );
        addActor( table );
        table.align( Align.top );

        Cell<?> cell = table.add( tabsTable ).expandX().fillX().align( Align.left );
        table.row();
        table.add( contents ).expandX().fillX();
        cell.expandX().fillX();
    }

    public void addTab( final Button b, final Actor thing ) {
        tabsTable.add( b );
        buttons.add( b );
        b.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                if ( b.isChecked() )
                    // switch out the contents
                    contents.setActor( thing );
            }
        } );
        // if this is the first button, then we need to make it the selected one
        if (buttons.getButtons().size == 1) {
            contents.setActor( thing );
        }
    }

    @Override
    public float getPrefWidth() {
        return Math.max( tabsTable.getPrefWidth(), contents.getPrefHeight() );
    }

    @Override
    public float getPrefHeight() {
        return tabsTable.getPrefHeight() + contents.getPrefHeight();
    }

    @Override
    public float getMinWidth() {
        return Math.max( tabsTable.getMinWidth(), contents.getMinWidth() );
    }

    @Override
    public float getMinHeight() {
        return tabsTable.getMinHeight() + contents.getMinHeight();
    }

    @Override
    public float getMaxHeight() {
        return tabsTable.getMaxHeight() + contents.getMaxHeight();
    }

    @Override
    public float getMaxWidth() {
        return Math.min( tabsTable.getMinWidth(), contents.getMinWidth() );
    }
}
