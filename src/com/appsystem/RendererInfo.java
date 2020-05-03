package com.appsystem;

import com.appsystem.AnimRenderer;
import com.symbol.Symbol;
import com.symbol.SymbolParent;
import processing.core.PApplet;
import processing.core.PGraphics;

import static processing.core.PApplet.ceil;
import static processing.core.PApplet.floor;

public class RendererInfo{
    int  width;
    int height;
    String renderer;

    public RendererInfo(float dWidth, float dHeight, String dRenderer) {
        width=ceil(dWidth); height=ceil(dHeight); renderer=dRenderer;
    }


    public PGraphics createRenderGraphics(PApplet applet){
        return applet.createGraphics(width,height,renderer);
    }
    public AnimRenderer createRenderer(PApplet applet,int frameNum, Symbol mainScene){
        return new AnimRenderer(createRenderGraphics(applet), (SymbolParent) mainScene,frameNum);
    }
}