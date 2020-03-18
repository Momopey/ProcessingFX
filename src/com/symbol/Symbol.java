package com.symbol;


import com.mode.Mode;
import com.timeline.Timeline;
import com.util.SimpleMatrix;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.core.PMatrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Symbol implements SymbolFunctionality{
    protected SimpleMatrix transformDefault;
    protected PMatrix transformMatrix;
    protected float alpha;
    public float alphaMult;
    protected PMatrix matrix;
    protected Timeline timeline;

    public Set<Mode> modes;
    public Symbol(PVector dPos,PVector dCenter,float dAngle,float dScale,float dAlpha){
        setTransform(new SimpleMatrix(dPos,dCenter,dAngle,dScale));
//        position=dPos;center=dCenter; angle=dAngle; scale=dScale
        alpha=alphaMult=dAlpha;
        timeline= new Timeline(this);
        modes= new HashSet<Mode>();

    }

    public SimpleMatrix getTransformDefault() {
        return transformDefault;
    }

    public void setTransformDefault(SimpleMatrix transformDefault) {
        this.transformDefault = transformDefault;
    }

    public void setTransform(SimpleMatrix newTransform){
        transformDefault=newTransform;
        returnToDefault();
    }
//    public  void applyDefaultTransform(SimpleMatrix product){
//        setTransform();
//    }
    public void applyTransform(PMatrix transform){
        transformMatrix.apply(transform);
    }
    public void returnToDefault(){
        transformMatrix=transformDefault.getMatrix();
    }

    public PVector getPosition() {
        return transformDefault.getPosition();
    }

    public PVector getCenter() {
        return transformDefault.getCenter();
    }

    public float getAngle() {
        return transformDefault.getAngle();
    }

    public float getScale() {
        return transformDefault.getScale();
    }

    public float getAlpha() {
        return alpha;
    }

    public float getAlphaMult() {
        return alphaMult;
    }


    public void render(PGraphics graphic){
        matrix= graphic.getMatrix();
        graphic.applyMatrix(transformMatrix);
       draw(graphic);
       graphic.setMatrix(matrix);
    }
    public void draw(PGraphics graphic){
        //CHILD IMPLEMENT
    }
    public void fill(PGraphics graphic,Color fillColor){
        graphic.fill(fillColor.getRed(),fillColor.getGreen(),fillColor.getBlue(),fillColor.getAlpha());
    }
    public void stroke(PGraphics graphic,Color strokeColor){
        graphic.stroke(strokeColor.getRed(),strokeColor.getGreen(),strokeColor.getBlue(),strokeColor.getAlpha());
    }
    public void fill(PGraphics graphic,Color fillColor,float alphaMult){
        graphic.fill(fillColor.getRed(),fillColor.getGreen(),fillColor.getBlue(),fillColor.getAlpha()*alphaMult);
    }
    public void stroke(PGraphics graphic,Color strokeColor,float alphaMult){
        graphic.stroke(strokeColor.getRed(),strokeColor.getGreen(),strokeColor.getBlue(),strokeColor.getAlpha()*alphaMult);
    }
    public void addMode(Mode mode){
        modes.add(mode);
    }
    public boolean usesMode(Mode useMode){
        for(Mode mode: modes){
            if(mode==useMode){
                return true;
            }
        }
        return false;
    }
    public void loadFrame(int dFrameNumber){
        timeline.loadFrame(dFrameNumber);
    }
    public Timeline getTimeline(){
        return timeline;
    }
}
