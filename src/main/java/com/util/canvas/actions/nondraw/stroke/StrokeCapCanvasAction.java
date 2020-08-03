package com.util.canvas.actions.nondraw.stroke;

import com.util.canvas.CanvasAction;
import processing.core.PGraphics;

public class StrokeCapCanvasAction implements CanvasAction {
    int cap;
    public StrokeCapCanvasAction(int dCap){
        cap=dCap;
    }
    @Override
    public void Run(PGraphics graphic) {
        graphic.strokeCap(cap);
    }
}
