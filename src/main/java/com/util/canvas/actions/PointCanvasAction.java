package com.util.canvas.actions;

import com.util.canvas.CanvasAction;
import com.util.canvas.DrawCanvasAction;
import processing.core.PGraphics;

public class PointCanvasAction extends DrawCanvasAction {
    float x;
    float y;

    public PointCanvasAction(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void Run(PGraphics graphic) {
        graphic.point(x,y);
    }
}
