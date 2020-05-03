package com.appsystem;

import processing.core.PGraphics;
import com.symbol.SymbolParent;
import processing.core.PShape;

import java.awt.*;

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
            graphicsRender.beginDraw();
            graphicsRender.background(255);
            graphicsRender.fill(0);
            graphicsRender.text("Frame loaded:" + frameNumber, 10, 20);

            if(frameNumber>=1) {
                scene.loadFrame(frameNumber - 1);
//                scene.setAlpha(0.2f);
                scene.setTint(new Color(255,230,230,51));
                scene.render(graphicsRender);
            }
            if(frameNumber<=scene.getTimeline().length-1) {
                scene.loadFrame(frameNumber + 1);
                scene.setTint(new Color(230,255,230,51));
                scene.render(graphicsRender);
            }
            scene.loadFrame(frameNumber);
            scene.setTint(new Color(255,255,255,255));
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
    public void render(){
        try {
            graphicsRender.beginDraw();
            graphicsRender.background(255);
            scene.loadFrame(frameNumber);
            scene.setTint(new Color(255, 255, 255, 255));
            scene.render(graphicsRender);
            graphicsRender.endDraw();
        }catch (Exception e)
        {
            // Throwing an exception
            System.out.println ("Exception is caught:");
            e.printStackTrace();
        }
    }
}
