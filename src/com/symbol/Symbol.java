package com.symbol;


import com.appsystem.RendererInfo;
import com.debug.Debugable;
import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.Timeline;
import com.util.SimpleMatrix;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import processing.core.*;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static processing.core.PApplet.floor;

// The base class of all animated objects in Panim(to be renamed). Inherits some necicary functionality from the interface
public class Symbol implements SymbolFunctionality, Debugable {
    public Color getTint() {
        return tint;
    }
    public void setTint(Color tint) {
        this.tint = tint;
        this.absoluteTint=tint;
        fixTint();
    }
    public Color getAbsoluteTint() {
        return absoluteTint;
    }
    public void fixTint() {
//        System.out.println(Name+" fix tint , new alpha "+absoluteTint.getAlpha());
//        absoluteTint=tint(tint,parentContainer.getActiveParent().getAbsoluteTint());
    }
    public void setAlpha(float alpha){
        setTint(new Color(tint.getRed(),tint.getGreen(),tint.getBlue(),floor(255*alpha)));
    }

    public PGraphics graphicsRender;
    protected SimpleMatrix spaceMatrix;
    protected SimpleMatrix transformMatrix;
//    protected float width;
//    protected float height;
//    protected float alpha;
    protected Color tint;
    protected Color absoluteTint;
    protected PMatrix matrix;
    protected Timeline timeline;
    protected String Name;

    public Set<Mode> modes;
    public enum RenderMode{STANDARD, PGRAPHIC}
    protected RenderMode renderMode;
    public Symbol(PVector dPos,PVector dCenter,float dAngle,float dScale,float dAlpha){
        SimpleMatrix defaultSpace=new SimpleMatrix(dPos,dCenter,dAngle,dScale);
        setSpaceMatrix(defaultSpace);
        timeline= new Timeline(this);
        System.out.println(timeline.spaceController);
        timeline.spaceController.SetDefaultSpace(defaultSpace);
        modes= new HashSet<Mode>();
        addMode(PAnim.MODETimelineSpace);
        addMode(PAnim.MODETimelineMotion);
        tint= new Color(255,255,255,floor(255f*dAlpha));
        absoluteTint= new Color(255,255,255,floor(255f*dAlpha));
        graphicsRender = PAnim.processing.renderInfo.createRenderGraphics(PAnim.processing);
        renderMode=RenderMode.STANDARD;
    }
    public void fixSymbol(){
        timeline.fixTimeline();
    };

    public SimpleMatrix getSpaceMatrix() {
        return spaceMatrix;
    }

    public void setSpaceMatrix(SimpleMatrix dTransformSpace) {
        this.spaceMatrix = dTransformSpace;
    }

    public SimpleMatrix getTransformMatrix() {
        return transformMatrix;
    }

    public void applyTransform(SimpleMatrix transform){
        transformMatrix.apply(transform);
    }

    public void refreshDefault(){
        transformMatrix= new SimpleMatrix();
    }


    public PVector getPosition() {
        return spaceMatrix.getPosition();
    }

    public PVector getCenter() {
        return spaceMatrix.getCenter();
    }

    public float getAngle() {
        return spaceMatrix.getAngle();
    }

    public float getScale() {
        return spaceMatrix.getScale();
    }




//    public void render(PShape graphic){
//        matrix= graphic.m
//        SimpleMatrix thisTransform= spaceMatrix.clone();
//        thisTransform.apply(transformMatrix);
//        graphic.applyMatrix(thisTransform.getMatrix());
//        graphicsRender.beginDraw();
//        graphicsRender.clear();
//        draw(graphicsRender);
//        graphicsRender.endDraw();
//        graphic.image(graphicsRender,0,0);
//        graphic.setMatrix(matrix);
//    }

    public void render(PGraphics graphic){
        if(renderMode==RenderMode.PGRAPHIC) {
            matrix = graphic.getMatrix();
            graphic.resetMatrix();
            SimpleMatrix thisTransform = spaceMatrix.clone();
            thisTransform.apply(transformMatrix);
            PMatrix2D matrixclone = (PMatrix2D) matrix.get();
            matrixclone.apply(thisTransform.getMatrix());
            graphicsRender.beginDraw();
            graphicsRender.applyMatrix(matrixclone);
            graphicsRender.clear();
            graphic.tint(tint.getRed(), tint.getGreen(), tint.getBlue(), tint.getAlpha());
            draw(graphicsRender);
            graphicsRender.endDraw();
            graphic.image(graphicsRender, 0, 0);
            graphic.setMatrix(matrix);

        }else if(renderMode==RenderMode.STANDARD) {

            graphic.pushMatrix();
            SimpleMatrix thisTransform = spaceMatrix.clone();
            thisTransform.apply(transformMatrix);
            graphic.applyMatrix(thisTransform.getMatrix());
            draw(graphic);
            graphic.popMatrix();
        }
    }
//    public void render(PGraphics graphic){

//    }

//    public void render(PShape shape){
//        shape.ma
//    }
    public void draw(PGraphics graphic){
        //CHILD IMPLEMENT
    }

    public void addMode(Mode mode){
        modes.add(mode);
    }
    public boolean usesMode(Mode useMode){
        return modes.contains(useMode);
    }
    public void loadFrame(int dFrameNumber){
        timeline.loadFrame(dFrameNumber);
    }
    public Timeline getTimeline(){
        return timeline;
    }

    public void drawSymbolTimeline(Canvas editorCanvas){
//        System.out.println("Timeline:"+getTimeline().length);
        int length=100;
        int height=timeline.controllers.size()+2;
        int numControllers=timeline.controllers.size();
        GraphicsContext editorGC=editorCanvas.getGraphicsContext2D();;
        editorCanvas.widthProperty().setValue((12*(6+length)));
        editorGC.setFill(javafx.scene.paint.Color.WHITE);
        editorGC.fillRect(0, 0, editorCanvas.getWidth(), editorCanvas.getHeight());
        editorGC.setFill(new javafx.scene.paint.Color(0,0,0,0.03));
        editorGC.fillRoundRect(0,0,12*(6+length),15+25*6+15,10,10);
        editorGC.fillRoundRect(12*6,0,12*(6+length),15+25*numControllers,10,10);
        editorGC.fillRoundRect(12*6,0,12*(6+length),15+25*height+15,10,10);
        editorGC.fillRoundRect(0,15,12*(6+length),15+25*6,10,10);
        editorGC.setFont(new javafx.scene.text.Font(12));
        editorGC.setFill(javafx.scene.paint.Color.BLACK);
        editorGC.fillText(getName(),8,11);
        editorGC.setLineWidth(1);
        editorGC.setFont(new Font(10));
        for(int i=0;i<length;i++) {
            editorGC.setFill(new javafx.scene.paint.Color(0,0,0,0.4));
            editorGC.fillText(String.valueOf(i%10),12*(6+i)+2,11);
            if(i%5==0){
                editorGC.setFill(new javafx.scene.paint.Color(0,0,0,0.06));
                editorGC.fillRect(12 * (6 + i), 15,12,25 * height);
            }else{
                editorGC.setStroke(new javafx.scene.paint.Color(0,0,0,0.06));
                editorGC.strokeLine(12 * (6 + i), 15, 12 * (6 + i), 15 + 25 * height);
            }
        }
        editorGC.setStroke(new javafx.scene.paint.Color(0,0,0,0.06));
        for(int i=0;i<height+1;i++){
            editorGC.strokeLine(12 * (1), 15+25*i, 12 * (6 + length), 15+25*i);
        }
        editorGC.setStroke(new javafx.scene.paint.Color(0,0,0,0.1));
        editorGC.strokeLine(12 * (1), 15+25*numControllers, 12 * (6 + length), 15+25*numControllers);
        timeline.drawTimelineInEditor(editorGC);



    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getDebugInfo() {
        return null;
    }

    @Override
    public Debugable setName(String newName) {
        Name=newName;
        return this;
    }
    public Symbol setRenderMode(RenderMode dRenderMode){
        renderMode=dRenderMode;
        return this;
    }

    public void fill(PGraphics graphic,Color fillColor){
        Color inContext=colorInContext(fillColor);
        graphic.fill(inContext.getRed(),inContext.getGreen(),inContext.getBlue(),inContext.getAlpha());
    }
    public void stroke(PGraphics graphic,Color strokeColor){
        Color inContext=colorInContext(strokeColor);
        graphic.stroke(inContext.getRed(),inContext.getGreen(),inContext.getBlue(),inContext.getAlpha());
    }

    public static Color tint(Color A,Color B){
        return new Color(A.getRed()*B.getRed()/255,A.getGreen()*B.getGreen()/255,A.getBlue()*B.getBlue()/255,A.getAlpha()*B.getAlpha()/255);
    }
    public Color colorInContext(Color C){
        if(renderMode==RenderMode.PGRAPHIC){
            return C;
        }else if(renderMode==RenderMode.STANDARD){
            return tint(absoluteTint,C);
        }
        return C;
    }

}