package com.appsystem;

import processing.core.PGraphics;
import com.symbol.SymbolParent;

public class AnimRenderer extends Thread {
    public PGraphics graphicsRender;
    public SymbolParent scene;
    public int frameNumber;
    public AnimRenderer(PGraphics dRender, SymbolParent dScene, int dFrameNumber){
        graphicsRender=dRender;
        scene=dScene;
        frameNumber=dFrameNumber;
    }

    public void run()
    {
        try
        {
            scene.loadFrame(frameNumber);
            graphicsRender.beginDraw();
            graphicsRender.background(255);
            graphicsRender.fill(0);
            graphicsRender.text("Frame loaded:"+frameNumber,10,20);
            scene.render(graphicsRender);
            graphicsRender.endDraw();
        }
        catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught:");
            e.printStackTrace();
        }

    }
}
