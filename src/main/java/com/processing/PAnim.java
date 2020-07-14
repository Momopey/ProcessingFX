package com.processing;

import com.appsystem.AnimRenderer;
import com.appsystem.RendererInfo;
import com.javafx.App;
import com.javafx.Controller;
import com.mode.Mode;
import com.mode.ModeTimelineMotion;
import com.mode.ModeTimelineSpace;
import com.symbol.SymbolContainer;
import com.symbol.Symbol;
import com.symbol.ParentSymbol;
import com.symbol.usermade.BallSymbol;
import com.timeline.keyframe.motion.TransformSpaceMotionKeyframe;
import com.timeline.keyframe.motion.TweenTransformMotionKeyframe;
import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframeContainer.MotionKeyframeContainer;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;
import javafx.application.Application;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PSurface;
import processing.core.PVector;
import processing.javafx.PSurfaceFX;

import java.io.IOException;
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
                if(frame>mainScene.getTimeline().length){
                    frame=mainScene.getTimeline().length;
                }
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

    public RendererInfo renderInfo= new RendererInfo(1280,720,FX2D_CLEAN);
    public PlaybackInfo playbackInfo= new PlaybackInfo();

    public ParentSymbol mainScene;
    public SymbolContainer mainContainer;
    public static PGraphics graphicsWindow;
    public ArrayList<SymbolContainer> savedContainers;
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

    public void initSymbolsKeyframes(){
        mainScene= (ParentSymbol) new ParentSymbol(new PVector(20,20),new PVector(0,0),0f,0.3f,1f, mainContainer).setName("MainScene");//graphicsWindow.width/2,graphicsWindow.height/2

        System.out.println("Mainscene length"+ mainScene.getTimeline().length);;
        mainScene.getTimeline().setSize(0,100);
        KeyframeContainer mainTranslationController=new MotionKeyframeContainer(mainScene.getTimeline());

        TweenStartTranslationKeyframe=new TweenTransformMotionKeyframe(20).setNewTransform(new SimpleMatrix(new PVector(0,0),new PVector(0,0),0,1f)).setName("Tween start translation");
        mainTranslationController.addKeyFrame(TweenStartTranslationKeyframe);

        TweenEndTranslationKeyframe=new TransformSpaceMotionKeyframe(50,1).setNewTransform(new SimpleMatrix(new PVector(200,200),new PVector(0,0),0.0f,0.4f)).setName("Tween end translation");
        mainTranslationController.addKeyFrame(TweenEndTranslationKeyframe);

//        mainScene.getTimeline().addTimelineController(mainTranslationController);

        KeyframeContainer mainRotationController=new MotionKeyframeContainer(mainScene.getTimeline());

        TweenStartRotationKeyframe=new TweenTransformMotionKeyframe(20).setNewTransform(new SimpleMatrix(new PVector(0,0),new PVector(0,0), (float) 0,1f)).setName("Tween Start rotation");
        mainRotationController.addKeyFrame(TweenStartRotationKeyframe);

//        TweenEndRotationKeyframe =new TransformSpaceMotionKeyframe(20+40,1).setNewTransform(new SimpleMatrix(new PVector(400,100),new PVector(0,0), (float) 0.2,1f)).setName("Tween End rotation");
        TweenEndRotationKeyframe =new TransformSpaceMotionKeyframe(20+40,1).setNewSpaceMatrix(new SimpleMatrix(new PVector(40,50),new PVector(0,0), (float) 0.2,0.8f)).setName("Tween End rotation");
        mainRotationController.addKeyFrame(TweenEndRotationKeyframe);
//        mainScene.getTimeline().addTimelineController(mainRotationController);
//        BallSymbol circle = (BallSymbol) new BallSymbol(mainChildren, new PVector(100, 100), new PVector(100, 100), 100).setName("CircleSymbol");
//        circle.getTimeline().setSize(0, 1000);
//            TimelineController BallController = new TimelineControllerMotion(circle.getTimeline());
//            BallController.addKeyFrame(new TweenTransformMotionKeyframe(0).setNewTransform(new SimpleMatrix(new PVector(50, 50), new PVector(0, 0), 0, 1)).setName("circleTransform1"));
//            BallController.addKeyFrame(new TransformSpaceMotionKeyframe(100, 1, new SimpleMatrix(new PVector(100, 50), new PVector(100, 100), 0.02, 1.1f)).setName("circleTransform"));
//            circle.getTimeline().addTimelineController(BallController);

        for(int i=0;i<1;i++) {
            BallSymbol circle = (BallSymbol) new BallSymbol(mainContainer, new PVector(random(graphicsWindow.width), random(graphicsWindow.height)), new PVector(100, 100), 50).setRenderMode(Symbol.RenderMode.STANDARD);
            circle.getTimeline().setSize(0, 1000);
            KeyframeContainer BallController = new MotionKeyframeContainer(circle.getTimeline());
            BallController.addKeyFrame(new TweenTransformMotionKeyframe(0).setNewTransform(new SimpleMatrix(new PVector(50,50), new PVector(100, 100), 0, 1)).setName("circleTransform1"));
            BallController.addKeyFrame(new TransformSpaceMotionKeyframe(100, 1).setNewTransform(new SimpleMatrix(new PVector(random(100)-100, random(100)-100), new PVector(0, 0), random(1)-.3f, random(2))).setName("circleTransform"));
            circle.getTimeline().addTimelineController(BallController);
            for(int j=0;j<10;j++){
                BallSymbol circle2 = (BallSymbol) new BallSymbol(circle.getSymbolContainer(), new PVector(4, 4), new PVector(0, 0), 50).setRenderMode(Symbol.RenderMode.STANDARD);
                circle2.getTimeline().setSize(0, 1000);
//                KeyframeContainer BallController2= new MotionKeyframeContainer(circle2.getTimeline());
//                BallController2.addKeyFrame(new TweenTransformMotionKeyframe(0).setNewTransform(new SimpleMatrix(new PVector(50,50), new PVector(100, 100), 0, 1)).setName("circleTransform1"));
//                BallController2.addKeyFrame(new TransformSpaceMotionKeyframe(100, 1).setNewTransform(new SimpleMatrix(new PVector(random(100)-100, random(100)-100), new PVector(0, 0), random(1)-.3f, random(2))).setName("circleTransform"));
//                circle2.getTimeline().addTimelineController(BallController2);
            }
        }
    }

    @Override
    public void setup() {
        Controller.p = this;
        background(255);
        strokeWeight(5);
        processing= this;
        LoadModes();

        savedContainers= new ArrayList<SymbolContainer>();

        graphicsWindow= renderInfo.createRenderGraphics(this);
        mainContainer = new SymbolContainer();
        savedContainers.add(mainContainer);

        initSymbolsKeyframes();
        renderer= renderInfo.createRenderer(0,mainScene);
        renderer.start();
    }
    int framesSpent=0;
    @Override
    public void draw(){
        framecount.setText(String.valueOf(frameCount));
        framerate.setText(String.valueOf(frameRate));
        canvaswidth.setText(String.valueOf(width));
        background(255);
        rect(100,100,100,100);

        if(!renderer.isAlive()) {
//            System.out.println("FrameSpent:"+framesSpent);
            framesSpent=0;
            int nextframe = playbackInfo.nextFrame();
//            System.out.println("Nextframe:"+nextframe+" prevFrame:"+playbackInfo.prevframe);
//            graphicsWindow = renderer.graphicsRender;
            if (playbackInfo.updated) {
                System.out.println("the state has been updated");
                ;
            }
            if (nextframe != playbackInfo.prevframe || playbackInfo.updated) {
                renderer = renderInfo.createRenderer( nextframe, mainScene);
                renderer.start();
//                System.out.println("RENDERING FRAME"+ nextframe);
            }
            playbackInfo.prevframe = playbackInfo.frame;
            playbackInfo.updated = false;
        }
        framesSpent++;
        image(graphicsWindow,0,0);

//        mainScene.getTimeline().drawTimelineInEditor(Controller.controller.editorGC);
        mainScene.drawSymbolTimeline(Controller.controller.editorCanvas);
        playbackInfo.drawFrameRay(Controller.controller.editorGC);
//        TweenStartTranslationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);
//        TweenEndTranslationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);
//        TweenStartRotationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);
//        TweenEndRotationKeyframe.drawKeyframeInTimeline(Controller.controller.editorGC);

    }
    public void exportScene() throws IOException {
        File file= new File("/Users/martinlu/Desktop/out/temp_img");
        file.mkdir();
        for(int i=0;i<mainScene.getTimeline().length;i++){
            renderer= renderInfo.createRenderer(i,mainScene,"/Users/martinlu/Desktop/out/temp_img/img-"+String.format("%05d",i)+".png");
            renderer.run();
//            while(renderer.isAlive()){
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            System.out.println("Rendered frame:"+i);
        }
        System.out.println("ALL I EVER DO IS SCUFF MYSELF");
        FFmpeg ffmpeg = new FFmpeg("/usr/local/Cellar/ffmpeg/4.2.2_3/bin/ffmpeg");

        FFmpegBuilder builder =  new FFmpegBuilder()
                .addInput("/Users/martinlu/Desktop/out/temp_img/img-%05d.png")
                .addOutput("/Users/martinlu/Desktop/out/final_vids/output.mp4")
                .setVideoPixelFormat("yuv420p")
                .setVideoResolution(1920,1080)
                .setVideoCodec("libx264")
                .setVideoFrameRate(FFmpeg.FPS_24)
                .done();
        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg);
        executor.createJob(builder).run();

    }

    @Override
    public void mouseDragged() {
        line(mouseX, mouseY, pmouseX, pmouseY);
    }

    public void redraw() {
        background(bgColor);
    }
}