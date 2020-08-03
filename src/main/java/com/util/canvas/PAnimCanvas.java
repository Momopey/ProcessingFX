package com.util.canvas;

import com.util.canvas.actions.*;
import com.util.canvas.actions.nondraw.NoFillCanvasAction;
import com.util.canvas.actions.nondraw.RectModeCanvasAction;
import com.util.canvas.actions.nondraw.stroke.NoStrokeCanvasAction;
import com.util.canvas.actions.nondraw.stroke.StrokeCapCanvasAction;
import com.util.canvas.actions.nondraw.stroke.StrokeJoinCanvasAction;
import com.util.canvas.actions.nondraw.stroke.StrokeWeightCanvasAction;
import processing.core.PGraphics;
import processing.core.PMatrix2D;

import java.util.ArrayList;

public class PAnimCanvas{
    PMatrix2D initialTransform;
    ArrayList<CanvasAction> actions = new ArrayList<>();

    void clear() {
        actions = new ArrayList<>();
    }

    void renderTo(PGraphics graphic) {
        initialTransform = (PMatrix2D) graphic.getMatrix();
        for (int i = 0; i < actions.size(); i++) {
            actions.get(i).Run(graphic);
        }
        graphic.setMatrix(initialTransform);
    }

    void addAction(CanvasAction action) {
        actions.add(action);
    }

    // Standard Pgraphics functions::::
    public void beginDraw() {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void endDraw() {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }

    public void point(float x, float y) {
        addAction(new PointCanvasAction(x,y));
    }

    public void line(float x1, float y1, float x2, float y2) {
        addAction(new LineCanvasAction(x1,y1,x2,y2));
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        addAction(new TriangleCanvasAction(x1,y1,x2,y2,x3,y3));
    }
    public void quad(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4) {
    }
    public void rectMode(int mode) {
        addAction(new RectModeCanvasAction(mode));
    }
    public void rect(float a, float b, float c, float d) {
        addAction(new RectCanvasAction(a,b,c,d));
    }
    public void rect(float a, float b, float c, float d, float r){
        addAction(new RectRoundedCanvasAction(a,b,c,d,r,r,r,r));
    }
    public void rect(float a, float b, float c, float d, float tl, float tr, float br, float bl) {
        addAction(new RectRoundedCanvasAction(a,b,c,d,tl,tr,br,bl));
    }

    //todo
    public void ellipseMode(int mode){}
    public void ellipse(float a, float b, float c, float d) {}
    public void arc(float a, float b, float c, float d, float start, float stop) {}
    public void arc(float a, float b, float c, float d, float start, float stop, int mode) {}




    ////
    public void strokeWeight(float weight) {
        addAction(new StrokeWeightCanvasAction(weight));
    }

    public void strokeJoin(int join) {
        addAction(new StrokeJoinCanvasAction(join));
    }

    public void strokeCap(int cap) {
        addAction(new StrokeCapCanvasAction(cap));
    }

    public void noStroke() {
        addAction(new NoStrokeCanvasAction());
    }

    public void stroke(int rgb) {
    }

    public void stroke(int rgb, float alpha) {
    }

    public void stroke(float gray) {
    }

    public void stroke(float gray, float alpha) {
    }

    public void stroke(float v1, float v2, float v3) {
    }

    public void stroke(float v1, float v2, float v3, float alpha) {
    }

    ////////

    public void noFill() {
        addAction(new NoFillCanvasAction());
    }

    public void fill(int rgb) {
    }

    public void fill(int rgb, float alpha) {

    }

    public void fill(float gray) {

    }

    public void fill(float gray, float alpha) {

    }

    public void fill(float v1, float v2, float v3) {

    }

    public void fill(float v1, float v2, float v3, float alpha) {

    }
}
