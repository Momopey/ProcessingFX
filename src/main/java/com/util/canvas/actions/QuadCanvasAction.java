package com.util.canvas.actions;

import com.util.canvas.CanvasAction;
import com.util.canvas.DrawCanvasAction;
import processing.core.PGraphics;

public class QuadCanvasAction extends DrawCanvasAction {
    float x1;
    float y1;
    float x2;
    float y2;
    float x3;
    float y3;
    float x4;
    float y4;
    @Override
    public void Run(PGraphics graphic) {
        graphic.quad(x1,y1,x2,y2,x3,y3,x4,y4);
    }
}
