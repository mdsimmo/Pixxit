package com.github.mdsimmo.pixxit;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.github.mdsimmo.pixxit.tools.Pencil;
import com.github.mdsimmo.pixxit.utils.Color;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Drawing implements Iterable<Canvas> {

    private final List<Canvas> layers;
    private final Canvas temp;
    private Canvas activeLayer;
    private Color primaryColor, secondaryColor;
    private Tool tool;
    private final Selection selection;
    private final int width, height;

    public Drawing( int width, int height ) {
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        layers.add( activeLayer = new Canvas( width, height ) );
        temp = new Canvas( width, height );
        selection = new Selection( width, height );
        primaryColor = Color.BLACK;
        secondaryColor = Color.WHITE;
        tool = new Pencil();
    }

    /**
     * Gets a special "temporary" layer that can be used to show temporary status' on. This is often
     * used by the active tool to display un confirmed input
     * @return the temporary layer
     */
    public Canvas tempLayer() {
        return temp;
    }

    /**
     * Gets the layer that the user has selected. Most operations should take place on this layer
     * @return the layer currently being drawn on
     */
    public Canvas activeLayer() {
        return activeLayer;
    }

    /**
     * Gets all the layers of this drawing. Changes to the list will be reflected in the drawing
     */
    public List<Canvas> layers() {
        return layers;
    }

    /**
     * Sets what layer the user should operate on
     * @param layer the new active layer
     */
    public void setActiveLayer( int layer ) {
        activeLayer = layers.get( layer );
    }

    /**
     * Creates and appends a new layer for drawing. The new layer will also be set to be the active layer
     * @return the new layer
     */
    public Canvas newLayer() {
        Canvas canvas = new Canvas( width, height );
        layers.add( canvas );
        activeLayer = canvas;
        return canvas;
    }

    /**
     * Removes a layer from the drawing. If there are no layers remaining, then a new blank layer is
     * added
     * @param layer the layer index to remove
     * @return the removed layer
     */
    public Canvas remove( int layer ) {
        Canvas canvas = layers.remove( layer );
        if ( layers.isEmpty() )
            newLayer();
        if ( canvas == activeLayer ) {
            activeLayer = layers.get( Math.min( layer, layers.size()-1 ) );
        }
        return canvas;
    }

    /**
     * Gets what area of the drawing is selected
     * @return the selected area
     */
    public Selection selection() {
        return selection;
    }

    public Color primaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor( Color c) {
        this.primaryColor = c;
    }

    public Color secondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor( Color secondaryColor ) {
        this.secondaryColor = secondaryColor;
    }

    public Tool tool() {
        return tool;
    }

    public void setTool( Tool tool ) {
        this.tool = tool;
    }

    public int height() {
        return height;
    }

    public int width() {
        return width;
    }

    public void render( Batch batch, float x, float y, float scale ) {
        for ( Canvas canvas : layers )
            canvas.render( batch, x, y, scale );
        temp.render( batch, x, y, scale );
        selection.render( batch, x, y, scale );
    }

    @Override
    public Iterator<Canvas> iterator() {
        return layers().iterator();
    }
}
