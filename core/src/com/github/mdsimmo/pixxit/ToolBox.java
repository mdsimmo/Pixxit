package com.github.mdsimmo.pixxit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToolBox implements Iterable<Tool> {

    private List<Tool> tools = new ArrayList<Tool>();

    public List<Tool> tools() {
        return tools;
    }

    public ToolBox register(Tool tool) {
        tools.add( tool );
        return this;
    }

    @Override
    public Iterator<Tool> iterator() {
        return tools.iterator();
    }
}
