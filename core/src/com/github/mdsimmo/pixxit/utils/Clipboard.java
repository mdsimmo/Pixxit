package com.github.mdsimmo.pixxit.utils;

import com.github.mdsimmo.pixxit.Canvas;

public class Clipboard {

    private static Canvas content;

    public static void setContent( Canvas content ) {
        Clipboard.content = content;
    }

    public static Canvas getContent() {
        return content;
    }

}
