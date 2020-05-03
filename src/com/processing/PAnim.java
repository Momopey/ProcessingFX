package com.processing;

import com.appsystem.AnimRenderer;
import com.appsystem.RendererInfo;
import com.javafx.App;
import com.javafx.Controller;
import com.mode.Mode;
import com.mode.ModeTimelineMotion;
import com.mode.ModeTimelineSpace;
import com.symbol.ChildrenContainer;
import com.symbol.Symbol;
import com.symbol.SymbolParent;
import com.symbol.usermade.Circle2;
import com.timeline.controller.TimelineController;
import com.timeline.controller.TimelineControllerMotion;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.KeyframeMotionSpaceTransform;
import com.timeline.keyframe.motion.KeyframeMotionTransformTween;
import com.util.SimpleMatrix;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PSurface;
import processing.core.PVector;
import processing.javafx.PSurfaceFX;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.io.File;

public class PAnim extends PApplet {

    public int bgColor = 255;
    private Label framecount, framerate, canvaswidth;

    public static PAnim processing;
    AnimRenderer renderer;
//    class RenderInfo{
//        int  width=1280;
//        int height=720;
//        String renderer=JAVA2D;
//        public PGraphics createRenderGraphics(){
//            return createGraphics(renderInfo.width,renderInfo.height, renderInfo.renderer);
//        }
//        public AnimRenderer createRenderer(int frameNum){
//            return new AnimRenderer(createRenderGraphics(),mainScene,frameNum);
//        }
//    }


    public class PlaybackInfo{
        public int getFrame() {
            return frame;
        }

        public int getPrevframe() {
            return prevframe;
        }

        public boolean isUpdated() {
            return updated;
        }

        public boolean isPlayback() {
            return playback;
        }

        int frame=0;
        int prevframe=-1;
        boolean updated=false;
        public boolean playback=false;
        public void setFrame(int newFrameNumber){
            if(playback==false){
                frame=newFrameNumber;
//                System.out.println("New frame"+frame);
            }
        }
        public void incFrame(int inc){
            if(playback==false){
                frame+=inc;
//                System.out.println("New frame"+frame);
            }
        }
        public void setPlayback(boolean newPlayback){
            playback=newPlayback;
        }
        public int nextFrame(){
            Controller.controller.frameNumberSlider.setValue(frame);
            if(playback){
                frame++;
            }
            return frame;
        }
        public void updated(){
            updated=true;
        }

        public void drawFrameRay(GraphicsContext editorGC){
//            int frame=2;
            editorGC.setFill(new javafx.scene.paint.Color(0.40,0.4,0.9,0.8));
            editorGC.setStroke(new javafx.scene.paint.Color(0.4,0.4,0.4,0.80));
            editorGC.fillRect(12*(6+frame)+1,2,12,15-3);
            editorGC.strokeRect(12*(6+frame)+1,2,12,15-3);
            editorGC.setStroke(new javafx.scene.paint.Color(0.40,0.4,0.9,0.8));
            editorGC.setLineWidth(3);
            editorGC.strokeLine(12*(6+frame)+4+3,15,12*(6+frame)+4+3,15 + 25 * height+15);
        }
    }

    public RendererInfo renderInfo= new RendererInfo(1280,720,JAVA2D);
    public PlaybackInfo playbackInfo= new PlaybackInfo();

    public SymbolParent mainScene;
    public PGraphics graphicsWindow;
    public ArrayList<ChildrenContainer> savedContainers;
    public static Set<Mode> MODES;
    public static ModeTimelineMotion MODETimelineMotion;
    public static ModeTimelineSpace MODETimelineSpace;



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
        MODETimelineSpace= new ModeTimelineSpace();
        MODES.add(MODETimelineMotion);
        MODES.add(MODETimelineSpace);
    }

    Keyframe TweenStartTranslationKeyframe;
    Keyframe TweenEndTranslationKeyframe;
    Keyframe TweenStartRotationKeyframe;
    //Temporary interaction stuff
    public Keyframe TweenEndRotationKeyframe;
    public void debugInfo(){
        System.out.println("Debug info::::");
        System.out.println(TweenStartTranslationKeyframe.getDebugInfo());
        System.out.println(TweenEndTranslationKeyframe.getDebugInfo());
        System.out.println(TweenStartRotationKeyframe.getDebugInfo());
        System.out.println(TweenEndRotationKeyframe.getDebugInfo());
        System.out.println(mainScene.getTimeline().spaceController.getKeyframes().get(0).getDebugInfo());;
    }

    @Override
    public void setup() {
        Controller.p = this;
        background(255);
        strokeWeight(5);
        processing= this;
        LoadModes();

        savedContainers= new ArrayList<ChildrenContainer>();

        graphicsWindow= renderInfo.createRenderGraphics(this);
        ChildrenContainer mainChildren= new ChildrenContainer();
        savedContainers.add(mainChildren);
        mainScene= (SymbolParent) new SymbolParent(new PVector(20,20),new PVector(0,0),0f,0.3f,1f,mainChildren).setName("MainScene");//graphicsWindow.width/2,graphicsWindow.height/2

        mainScene.getTimeline().setSize(0,1000);
        System.out.println("Mainscene length"+ mainScene.getTimeline().length);;

        TimelineController mainTranslationController=new TimelineControllerMotion(mainScene.getTimeline());

        TweenStartTranslationKeyframe=new KeyframeMotionTransformTween(20).setNewTransform(new SimpleMatrix(new PVector(0,0),new PVector(0,0),0,1f)).setName("Tween start translation");
        mainTranslationController.addKeyFrame(TweenStartTranslationKeyframe);

        TweenEndTranslationKeyframe=new KeyframeMotionSpaceTransform(50,1).setNewTransform(new SimpleMatrix(new PVector(200,200),new PVector(0,0),0.0f,0.4f)).setName("Tween end translation");
        mainTranslationController.addKeyFrame(TweenEndTranslationKeyframe);

        mainScene.getTimeline().addTimelineController(mainTranslationController);

        TimelineController mainRotationController=new TimelineControllerMotion(mainScene.getTimeline());

        TweenStartRotationKeyframe=new KeyframeMotionTransformTween(20).setNewTransform(new SimpleMatrix(new PVector(0,0),new PVector(0,0), (float) 0,1f)).setName("Tween Start rotation");
        mainRotationController.addKeyFrame(TweenStartRotationKeyframe);

//        TweenEndRotationKeyframe =new KeyframeMotionSpaceTransform(20+40,1).setNewTransform(new SimpleMatrix(new PVector(400,100),new PVector(0,0), (float) 0.2,1f)).setName("Tween End rotation");
        TweenEndRotationKeyframe =new KeyframeMotionSpaceTransform(20+40,1).setNewSpaceMatrix(new SimpleMatrix(new PVector(80,50),new PVector(0,0), (float) 0.2,0.7f)).setName("Tween End rotation");
        mainRotationController.addKeyFrame(TweenEndRotationKeyframe);
        mainScene.getTimeline().addTimelineController(mainRotationController);

        //Tweening
//        mainRotationController.addKeyFrame(new KeyframeMotionTransformTween(20,new SimpleMatrix(new PVector(0,0),new PVector(0,100), (float) 0,1f)));
//        mainRotationController.addKeyFrame(new KeyframeMotionSpaceTransform(20+70,1,new SimpleMatrix(new PVector(100,100),new PVector(0,0), (float) 1.2,1.2f)));
//        mainRotationController.addKeyFrame(new KeyframeMotionTransformTween(90,new SimpleMatrix(new PVector(0,0),new PVector(0,100), (float) 0,1f)));
//        mainRotationController.addKeyFrame(new KeyframeMotionSpaceTransform(90+50,1,new SimpleMatrix(new PVector(1000,1000),new PVector(0,0), (float) 0,1f)));


//        mainRotationController.addKeyFrame(new KeyframeMotionStaticTransform(20,50,new SimpleMatrix(new PVector(200,200),new PVector(0,0), (float) 0.8,1.3f)));

//        mainScene.getTimeline().addTimelineController(mainTranslationController);


        //Garbage
        //        mainTranslationController.addKeyFrame(new KeyframeMotionTransformTween(70,new SimpleMatrix(new PVector(0,0),new PVector(0,0),0,1f)));
//        mainTranslationController.addKeyFrame(new KeyframeMotionSpaceTransform(100,1,new SimpleMatrix(new PVector(300,300),new PVector(0,0),-0,1f/0.4f)));


//        mainTranslationController.addKeyFrame(new KeyframeMotionTweenTransform(20,70,new SimpleMatrix(new PVector(0,0),new PVector(graphicsWindow.width/2,graphicsWindow.height/2),0f,1f)));
//        mainTranslationController.addKeyFrame(new KeyframeMotionStaticTransform(20,70,new SimpleMatrix(new PVector(0,0),new PVector(20,20),0f,1.3f)));
//        mainTranslationController.addKeyFrame(new KeyframeMotionStaticDefault(60,1,new SimpleMatrix(new PVector(0,0),new PVector(0,0),0f,1f)));
//

//
//        Circle2 circle = (Circle2) new Circle2(mainChildren, new PVector(100, 100), new PVector(100, 100), 50).setName("CircleSymbol");
//        circle.getTimeline().setSize(0, 1000);
//            TimelineController BallController = new TimelineControllerMotion(circle.getTimeline());
//            BallController.addKeyFrame(new KeyframeMotionTransformTween(0).setNewTransform(new SimpleMatrix(new PVector(50, 50), new PVector(100, 100), 0, 1)).setName("circleTransform1"));
//            BallController.addKeyFrame(new KeyframeMotionSpaceTransform(100, 1, new SimpleMatrix(new PVector(100, 100), new PVector(0, 0), 0.3f, 4f)).setName("circleTransform"));
//            circle.getTimeline().addTimelineController(BallController);

        for(int i=0;i<100;i++) {
            Circle2 circle = (Circle2) new Circle2(mainChildren, new PVector(random(1000), random(1000)), new PVector(100, 100), 50).setRenderMode(Symbol.RenderMode.STANDARD);
            circle.getTimeline().setSize(0, 1000);
            TimelineController BallController = new TimelineControllerMotion(circle.getTimeline());
            BallController.addKeyFrame(new KeyframeMotionTransformTween(0).setNewTransform(new SimpleMatrix(new PVector(50, 50), new PVector(100, 100), 0, 1)).setName("circleTransform1"));
            BallController.addKeyFrame(new KeyframeMotionSpaceTransform(100, 1, new SimpleMatrix(new PVector(100, 100), new PVector(0, 0), 0.3f, 4f)).setName("circleTransform"));
            circle.getTimeline().addTimelineController(BallController);
        }
        renderer= renderInfo.createRenderer(this,0,mainScene);
        renderer.start();
    }

    @Override
    public void draw(){
        framecount.setText(String.valueOf(frameCount));
        framerate.setText(String.valueOf(frameRate));
        canvaswidth.setText(String.valueOf(width));
        background(255);
        rect(100,100,100,100);
        if(!renderer.isAlive()){
            int nextframe=playbackInfo.nextFrame();
//            System.out.println("Nextframe:"+nextframe+" prevFrame:"+playbackInfo.prevframe);
            graphicsWindow = renderer.graphicsRender;
            if(playbackInfo.updated){
                System.out.println("the state has been updated");;
            }
            if(nextframe!=playbackInfo.prevframe||playbackInfo.updated) {
                renderer = renderInfo.createRenderer(this,nextframe,mainScene);
                renderer.start();
//                System.out.println("RENDERING FRAME"+ nextframe);
            }
            playbackInfo.prevframe=playbackInfo.frame;
            playbackInfo.updated=false;
        }
        image(graphicsWindow,0,0);


//        mainScene.getTimeline().drawTimelineInEditor(Controller.controller.editorGC);
        mainScene.drawSymbolTimeline(Controller.controller.editorCanvas);
        playbackInfo.drawFrameRay(Controller.controller.editorGC);
//        TweenStartTranslationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);
//        TweenEndTranslationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);
//        TweenStartRotationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);
//        TweenEndRotationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);

    }
    public void exportScene(){
        File file= new File("/Users/martinlu/Desktop/out");
        file.mkdir();
        for(int i=0;i<mainScene.getTimeline().length;i++){
            renderer= renderInfo.createRenderer(this,i,mainScene);
            renderer.start();
            while(renderer.isAlive()){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            renderer.graphicsRender.save("/Users/martinlu/Desktop/out/img-"+String.format("%05d",i)+".png");
        }
    }

    @Override
    public void mouseDragged() {
        line(mouseX, mouseY, pmouseX, pmouseY);
    }

    public void redraw() {
        background(bgColor);
    }
}
