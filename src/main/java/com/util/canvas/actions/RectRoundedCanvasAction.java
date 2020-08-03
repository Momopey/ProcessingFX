package com.util.canvas.actions;

import com.util.canvas.DrawCanvasAction;
import processing.core.PGraphics;

public class RectRoundedCanvasAction extends DrawCanvasAction {
    float a;
    float b;
    float c;
    float d;
    float tl;
    float tr;
    float br;
    float bl;
    public RectRoundedCanvasAction(float a, float b, float c, float d, float tl, float tr, float br, float bl) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.tl = tl;
        this.tr = tr;
        this.br = br;
        this.bl = bl;
    }
    @Override
    public void Run(PGraphics graphic) {
        graphic.rect(a,b,c,d,tl,tr,br,bl);
    }
}
