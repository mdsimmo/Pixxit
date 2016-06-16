package com.github.mdsimmo.pixxit.gui;

/**
 * Describes some basic information about a Cursor and the cursors history. This class is immutable
 */
public final class Cursor {

    public enum Button {
        LEFT, RIGHT, MIDDLE;
    }

    private final int xStart, yStart;
    private final int xPos, yPos;
    private final int xDelta, yDelta;
    private final int pointer;
    private final Button button;

    public Cursor( int x, int y, int xStart, int yStart, int xDelta, int yDelta, int pointer, Button button ) {
        this.xPos = x;
        this.yPos = y;
        this.xStart = xStart;
        this.yStart = yStart;
        this.xDelta = xDelta;
        this.yDelta = yDelta;
        this.pointer = pointer;
        this.button = button;
    }

    public int x() {
        return xPos;
    }

    public int y() {
        return yPos;
    }

    public int xStart() {
        return xStart;
    }

    public int yStart() {
        return yStart;
    }

    public int xDelta() {
        return xDelta;
    }

    public int yDelta() {
        return yDelta;
    }

    public int pointer() {
        return pointer;
    }

    public Button button() {
        return button;
    }
}