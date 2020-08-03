package com.util.canvas.actions.nondraw;

import com.util.canvas.CanvasAction;
import processing.core.PGraphics;

public class NoFillCanvasAction implements CanvasAction {
    @Override
    public void Run(PGraphics graphic) {
        graphic.noFill();
    }
}
