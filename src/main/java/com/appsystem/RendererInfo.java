package com.appsystem;

import com.processing.PAnim;
import com.symbol.Symbol;
import com.symbol.ParentSymbol;
import processing.core.PApplet;
import processing.core.PGraphics;

import static processing.core.PApplet.ceil;

public class RendererInfo{
    int  width;
    int height;
    String renderer;

    public RendererInfo(float dWidth, float dHeight, String dRenderer) {
        width=ceil(dWidth); height=ceil(dHeight); renderer=dRenderer;
    }


    public PGraphics createRenderGraphics(PApplet applet){
        return applet.createGraphics(width,height,renderer);
//        return PAnim.graphicsWindow;
    }

    public AnimRenderer createRenderer(int frameNum, Symbol mainScene){
        return new AnimRenderer(PAnim.graphicsWindow, (ParentSymbol) mainScene,frameNum);
    }
    public AnimRenderer createRenderer(int frameNum, Symbol mainScene,String saveTo){
        return new AnimRenderer(PAnim.graphicsWindow, (ParentSymbol) mainScene,frameNum,saveTo);
    }
}