package com.util.canvas.actions;

import com.util.canvas.CanvasAction;
import com.util.canvas.DrawCanvasAction;
import processing.core.PGraphics;

public class LineCanvasAction extends DrawCanvasAction {
    float x1;
    float y1;
    float x2;
    float y2;

    public LineCanvasAction(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void Run(PGraphics graphic) {
        graphic.line(x1,y1,x2,y2);
    }
}
