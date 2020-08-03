package com.util.canvas.actions.nondraw;

import com.util.canvas.CanvasAction;
import processing.core.PGraphics;

public class RectModeCanvasAction implements CanvasAction {
    int mode;

    public RectModeCanvasAction(int mode) {
        this.mode = mode;
    }

    @Override
    public void Run(PGraphics graphic) {
        graphic.rectMode(mode);
    }
}
