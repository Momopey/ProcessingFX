package com.util.canvas.actions.nondraw.stroke;

import com.util.canvas.CanvasAction;
import processing.core.PGraphics;

public class NoStrokeCanvasAction implements CanvasAction {
    @Override
    public void Run(PGraphics graphic) {
        graphic.noStroke();
    }
}
