package com.github.mdsimmo.pixxit;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.github.mdsimmo.pixxit.gui.ColorSelector;
import com.github.mdsimmo.pixxit.gui.PixxitStage;
import com.github.mdsimmo.pixxit.gui.views.DrawingView;
import com.github.mdsimmo.pixxit.gui.views.LayerView;
import com.github.mdsimmo.pixxit.gui.views.PalateView;
import com.github.mdsimmo.pixxit.gui.views.ToolBoxView;
import com.github.mdsimmo.pixxit.operations.*;
import com.github.mdsimmo.pixxit.tools.*;
import com.github.mdsimmo.pixxit.utils.Color;
import com.github.mdsimmo.pixxit.utils.KeyCode;

/**
 * The entry point into the code
 */
public class Pixxit extends ApplicationAdapter {
    Stage ui;

    @Override
    public void create () {
        // create the core tools
        Drawing drawing = new Drawing( 128, 128 );
        ToolBox tools = new ToolBox()
                .register( new Pencil() )
                .register( new Eraser() )
                .register( new Bucket() )
                .register( new Dropper() )
                .register( new RSelect() )
                .register( new Move() )
                .register( new Wand() );

        Operation[] operations = new Operation[] {
                new Copy(), new CopyLayer(),
                new Paste(),
                new Cut(), new CutLayer(),
                new Delete(), new DeleteLayer(),
                new SelectAll(), new DeselectAll(),
                new Merge(), new MergeDown(),
                new Save(),
        };

        // create the gui
        Skin skin = new Skin( Gdx.files.internal( "uiskin.json" ) );
        Viewport viewport = new ScreenViewport();
        ui = new PixxitStage( viewport );
        Gdx.input.setInputProcessor( ui );
        // set up operations
        ui.addListener( new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character ) {
                for ( Operation op : operations ) {
                    if (op.keyCode().isTriggered()) {
                        op.apply( drawing );
                        return true;
                    }
                }
                for (Tool tool : tools) {
                    KeyCode code = tool.keyCode();
                    if (code == null)
                        continue;
                    if (code.isTriggered()) {
                        drawing.setTool( tool );
                        return true;
                    }
                }
                return false;
            }
        });

        Table table = new Table();
        table.setFillParent( true );
        ui.addActor( table );

        // create the initial gui layout
        // in a future version, this layout should be read in from a text file
        SplitPane leftPane = new SplitPane(
                new ToolBoxView( skin, tools, drawing ),
                new LayerView( skin, drawing ),
                true, skin );
        leftPane.setSplitAmount( 0.3f );
        SplitPane rightPane = new SplitPane(
                new DrawingView( skin, drawing ),
                new PalateView( skin ),
                true, skin );
        Actor drawingPane = new DrawingView( skin, drawing );
        SplitPane rightHalf = new SplitPane( drawingPane, rightPane, false, skin );
        SplitPane window = new SplitPane( leftPane, rightHalf, false, skin );
        table.add( window ).expand().fill();

        float leftPaneWidth = 100, rightPaneWidth = 150;
        float windowWidth = Gdx.graphics.getWidth();
        window.setSplitAmount( leftPaneWidth / windowWidth );
        rightHalf.setSplitAmount( (windowWidth-leftPaneWidth-rightPaneWidth)/(windowWidth-leftPaneWidth) );
        rightPane.setSplitAmount( rightPaneWidth / Gdx.graphics.getHeight() ); // aka, a square
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ui.act();
        ui.draw();
    }

    @Override
    public void resize( int width, int height ) {
        ui.getViewport().update( width, height );
        ui.getViewport().apply( true );
    }
}
