package com.util.canvas.actions;

import com.util.canvas.CanvasAction;
import com.util.canvas.DrawCanvasAction;
import processing.core.PGraphics;

public class TriangleCanvasAction extends DrawCanvasAction {
    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;

    public TriangleCanvasAction(float x1, float y1, float x2, float y2, float x3, float y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public void Run(PGraphics graphic) {
        graphic.triangle(x1,y1,x2,y2,x3,y3);
    }
}
