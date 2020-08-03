package com.util.canvas.actions;

import com.util.canvas.CanvasAction;
import com.util.canvas.DrawCanvasAction;
import processing.core.PGraphics;

public class RectCanvasAction extends DrawCanvasAction {
    float a;
    float b;
    float c;
    float d;

    public RectCanvasAction(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    @Override
    public void Run(PGraphics graphic) {
        graphic.rect(a,b,c,d);
    }
}
