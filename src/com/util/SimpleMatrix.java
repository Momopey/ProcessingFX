package com.util;

import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PVector;

import static processing.core.PApplet.*;

public class SimpleMatrix implements Transform {
    protected PVector position;
    protected PVector center;
    protected Float angle;
    protected Float scale;
    public SimpleMatrix(PVector dPos,PVector dCenter,float dAngle,float dScale){
        position=dPos;center=dCenter; angle=dAngle; scale=dScale;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }

    public void setCenter(PVector center) {
        this.center = center;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public PVector getPosition() {
        return position;
    }

    public PVector getCenter() {
        return center;
    }

    public float getAngle() {
        return angle;
    }

    public float getScale() {
        return scale;
    }

    public SimpleMatrix lerpMatrix(SimpleMatrix target,float factor){
        return new SimpleMatrix(PVector.lerp(position,target.position, factor), PVector.lerp(center,center, factor),lerp(angle, target.angle, factor),lerp(scale, target.scale, factor));
    }

    public PMatrix getMatrix(){
        PMatrix matrix= new PMatrix2D();
        matrix.translate(position.x+center.x,position.y+center.y);
        matrix.rotate(angle);
        matrix.scale(scale);
        matrix.translate(-center.x,-center.y);
        return matrix;
    }

    public SimpleMatrix apply(SimpleMatrix p){
        PVector newPos= new PVector(p.center.x+p.position.x-center.x+p.scale*(cos(-p.angle)*(center.x+position.x-p.center.x)-sin(-p.angle)*(position.y+center.y-p.center.y)),
                p.position.y+p.center.y-center.y+p.scale*(cos(-p.angle)*(position.y+center.y-p.center.y)+sin(-p.angle)*(center.x+position.x-p.center.x)));
        newPos= new PVector(newPos.x*scale,newPos.y*scale);
        PVector newCenter= center.copy();
        return new SimpleMatrix(newPos,newCenter,angle+p.angle,scale*p.scale);
    }

    @Override
    public Transform clone() {
        return new SimpleMatrix(position.copy(),center.copy(),angle,scale);
    }
}
