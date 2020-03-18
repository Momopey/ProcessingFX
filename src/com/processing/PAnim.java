package com.processing;

import com.appsystem.AnimRenderer;
import com.javafx.App;
import com.javafx.Controller;
import com.mode.Mode;
import com.mode.ModeTimelineMotion;
import com.symbol.ChildrenContainer;
import com.symbol.usermade.Circle2;
import com.symbol.SymbolParent;
import com.timeline.controller.TimelineController;
import com.timeline.controller.TimelineControllerMotion;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.KeyframeMotionStaticDefault;
import com.timeline.keyframe.motion.KeyframeMotionStaticTransform;
import com.timeline.keyframe.motion.KeyframeMotionTweenTransform;
import com.util.SimpleMatrix;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PSurface;
import processing.core.PVector;
import processing.javafx.PSurfaceFX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PAnim extends PApplet {

    public int bgColor = 255;
    private Label framecount, framerate, canvaswidth;

    public static PAnim processing;
    AnimRenderer renderer;
    int frameNumber=1;

    public SymbolParent mainScene;
    public PGraphics graphicsWindow;
    public ArrayList<ChildrenContainer> savedContainers;
    public static Set<Mode> MODES;
    public static ModeTimelineMotion MODETimelineMotion;

    class RenderInfo{
        int  width=400;
        int height=400;
        String renderer=JAVA2D;
        public PGraphics createRenderGraphics(){
            return createGraphics(renderInfo.width,renderInfo.height, renderInfo.renderer);
        }
        public AnimRenderer createRenderer(int frameNum){
            return new AnimRenderer(createRenderGraphics(),mainScene,frameNum);
        }
    }
    RenderInfo renderInfo= new RenderInfo();

    @Override
    protected PSurface initSurface() {
        System.setProperty("glass.disableThreadChecks", "true");
        g = createPrimaryGraphics();
        PSurface genericSurface = g.createSurface();
        PSurfaceFX fxSurface = (PSurfaceFX) genericSurface;

        fxSurface.sketch = this;
        App.surface = fxSurface; // todo remove?
        Controller.surface = fxSurface;
        try {
            new Thread(()->{ Application.launch(App.class);}).start();
        }catch(IllegalStateException e){
        }

        while (fxSurface.stage == null) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
        }

        this.surface = fxSurface;
        Canvas canvas = (Canvas) surface.getNative();
        framecount = (Label) canvas.getScene().lookup("#frameCount");
        framerate = (Label) canvas.getScene().lookup("#frameRate");
        canvaswidth = (Label) canvas.getScene().lookup("#canvasWidth");
        return surface;
    }

    @Override
    public void settings() {
        size(0, 0, FX2D);
    }

    public void LoadModes(){
        MODES= new HashSet<Mode>();
        MODETimelineMotion=new ModeTimelineMotion();
        MODES.add(MODETimelineMotion);
    }

    @Override
    public void setup() {
        Controller.p = this;
        background(255);
        strokeWeight(5);
        processing= this;

        LoadModes();
        savedContainers= new ArrayList<ChildrenContainer>();

        graphicsWindow= renderInfo.createRenderGraphics();
        ChildrenContainer mainChildren= new ChildrenContainer();
        savedContainers.add(mainChildren);
        mainScene= new SymbolParent(new PVector(0,0),new PVector(graphicsWindow.width/2,graphicsWindow.height/2),0,0.4f,1,mainChildren);
        mainScene.addMode(MODETimelineMotion);
        mainScene.getTimeline().setSize(0,1000);

        TimelineController mainRotationController=new TimelineControllerMotion(mainScene.getTimeline(),new ArrayList<Keyframe>());
        mainRotationController.keyframes.add(new KeyframeMotionTweenTransform(mainRotationController,20,50,new SimpleMatrix(new PVector(0,0),new PVector(0,0),0,1f)));
        mainRotationController.keyframes.add(new KeyframeMotionStaticTransform(mainRotationController,20+50,1,new SimpleMatrix(new PVector(0,0),new PVector(0,0), (float) 0,1f)));

        TimelineController mainTranslationController=new TimelineControllerMotion(mainScene.getTimeline(),new ArrayList<Keyframe>());
        mainTranslationController.keyframes.add(new KeyframeMotionTweenTransform(mainTranslationController,20,50,new SimpleMatrix(new PVector(0,0),new PVector(graphicsWindow.width/2,graphicsWindow.height/2),0f,1f)));
        mainTranslationController.keyframes.add(new KeyframeMotionStaticDefault(mainTranslationController,20+50,100,new SimpleMatrix(new PVector(100,20),new PVector(graphicsWindow.width/2,graphicsWindow.height/2),0.2f,0.2f)));


        mainScene.getTimeline().addTimelineController(mainTranslationController);
        mainScene.getTimeline().addTimelineController(mainRotationController);
        Circle2 circle= new Circle2(mainChildren,new PVector(50,50),new PVector(4,2),50);
        renderer= renderInfo.createRenderer(0);
        renderer.start();
    }

    @Override
    public void draw() {
        framecount.setText(String.valueOf(frameCount));
        framerate.setText(String.valueOf(frameRate));
        canvaswidth.setText(String.valueOf(width));
        background(255);
//        rect(100,100,100,100);

        if(!renderer.isAlive()){
            graphicsWindow=renderer.graphicsRender;
            frameNumber++;
            renderer=renderInfo.createRenderer(frameNumber);
            renderer.start();
        }
        image(graphicsWindow,0,0);
    }

    @Override
    public void mouseDragged() {
        line(mouseX, mouseY, pmouseX, pmouseY);
    }

    public void redraw() {
        background(bgColor);
    }
}
