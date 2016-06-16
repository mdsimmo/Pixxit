package com.github.mdsimmo.pixxit.gui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.List;

/**
 * The FlowLayout arranges children in a row. When the bounds change, the children will wrap into
 * the next row
 */
public class FlowLayout extends WidgetGroup {

    private int align;
    private float padding;

    private List<Layout> layoutChildren() {
        List<Layout> children = new ArrayList<>();
        for (Actor child : getChildren() ) {
            if ( child instanceof Layout )
                children.add( (Layout)child );
        }
        return children;
    }

    @Override
    public void layout() {
        // find all widget things
        List<Layout> children = layoutChildren();

        final float width = getWidth();
        final float height = getHeight();

        float rowWidth = padding;
        float y = padding;
        int startIndex = 0;
        for (int i = 0; i < children.size(); i++) {
            Layout child = children.get( i );
            boolean makeNewRow = false;

            // do a first pass to determine the size of what is getting laid out
            if (width < rowWidth + child.getPrefWidth() + padding) {
                // this row is already too full. move to the next row
                makeNewRow = true;
                if ( i != startIndex)
                    i--; // don't include the last child in the layout below
                         // unless the element cannot fit onto its own row
                else
                    rowWidth += child.getPrefWidth() + padding;
            } else {
                // add up the row length
                rowWidth += child.getPrefWidth() + padding;
            }

            // make sure a new row gets made on the final row (even if it is uncompleted)
            if (i == children.size()-1) {
                makeNewRow = true;
            }

            if (makeNewRow) {
                // determine start position
                float x =  (align & Align.left) != 0 ? padding :
                           (align & Align.right) != 0 ? width - rowWidth + padding :
                           (int)((width - rowWidth)/2 + padding); // cast to int fixes 0.5 pixel blurriness
                float rowHeight = 0;
                // layout each actor in the row
                for ( int j = startIndex; j <= i; j++) {
                    Layout c = children.get( j );
                    float yy = (align & Align.top) != 0 ? height - y - c.getPrefHeight() : y;
                    ((Actor)c).setPosition( x, yy );
                    x += c.getPrefWidth() + padding;
                    rowHeight = Math.max( rowHeight, c.getPrefHeight() );
                }
                // reset for the next row
                y += rowHeight + padding;
                rowWidth = padding;
                startIndex = i+1;
            }
        }
    }

    public float getPadding() {
        return padding;
    }

    public void setPadding( float padding ) {
        this.padding = padding;
        invalidate();
    }

    public int getAlign() {
        return align;
    }

    public FlowLayout setAlign( int align ) {
        this.align = align;
        invalidate();
        return this;
    }

    @Override
    public float getMinWidth() {
        float width = 0;
        for (Layout child : layoutChildren())
            width = Math.max( width, child.getMinWidth() );
        return width;
    }

    @Override
    public float getMinHeight() {
        float height = 0;
        for (Layout child : layoutChildren())
            height = Math.max( height, child.getMinHeight() );
        return height;
    }

    public FlowLayout add( Actor ... actors ) {
        for (Actor actor : actors )
            addActor( actor );
        return this;
    }
}
