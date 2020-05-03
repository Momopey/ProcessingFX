package com.util;

import processing.core.PMatrix;
import processing.core.PMatrix2D;
import processing.core.PVector;

import java.util.ArrayList;

import static processing.core.PApplet.*;

public class SimpleMatrix implements Transform {
    protected PVector position;
    protected PVector center;
    protected Float angle;
    protected Float scale;
    public SimpleMatrix(PVector dPos,PVector dCenter,float dAngle,float dScale){
        position=dPos;center=dCenter; angle=dAngle; scale=dScale;
    }
    public SimpleMatrix(){
//        new PVector(0,0),new PVector(0,0),0.0f,1f
        position=new PVector(0,0);
        center= new PVector(0,0);
        angle=0f;
        scale=1f;
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
        return new SimpleMatrix(PVector.lerp(position,target.position, factor), PVector.lerp(center,target.center, factor),lerp(angle, target.angle, factor),lerp(scale, target.scale, factor));
    }

    public PMatrix getMatrix(){
        PMatrix matrix= new PMatrix2D();
        matrix.translate(position.x+center.x,position.y+center.y);
        matrix.rotate(angle);
        matrix.scale(scale);
        matrix.translate(-center.x,-center.y);
        return matrix;
    }

//    {c-d+p-p*s*cos(a)+P*s*sin(a),C+P-D-P*s*cos(a)-p*s*sin(a)}.{{1+s*cos(a),-s*sin(a)},{s*sin(a),1-s*cos(a)}}^-1
    //        PVector newP= new PVector(position.x+center.x-newc.x-center.x*scale*cos(angle)+center.y*scale*sin(angle),position.y+center.y-newc.y-center.y*scale*cos(angle)-center.x*scale*sin(angle));
//        PMatrix tempMatrix= new PMatrix2D(1+scale*cos(angle),scale*sin(angle),0,-scale*sin(angle),1-scale*cos(angle),0);
////        tempMatrix.transpose();
//        tempMatrix.invert();
    //        tempMatrix.mult(newP,temp);
//         temp= new PVector(-((scale * sin(angle) * (((position.y - newc.y) + center.y) - (center.y * scale * cos(angle)) - (center.x * scale * sin(angle)))) / (((1 - (scale * scale * cos(angle) * cos(angle))) + (scale * scale * sin(angle))) ^ 2)) + ((1 - scale*cos(angle)) (position.x- newc.x + center.x- center.x*scale*cos(angle) + center.y scale*sin(angle)))/(1 - s^2 cos(angle)^2 + s^2 sin(angle)^2),
//        ((1 + scale*cos(angle)) (position.y- newc.y + center.y - center.y*scale*cos(angle) - center.x*scale*sin(angle)))/(1 - s^2 cos(angle)^2 + s^2 sin(angle)^2) + (scale*sin(angle) (position.x- newc.x + center.x- center.x*scale*cos(angle) + center.y scale*sin(angle)))/(1 - s^2 cos(angle)^2 + s^2 sin(angle)^2))
    //   )-1)-dif.x*sin(angle));

    public void changeCenter(PVector newc){
        position=changeCenterNewPosition(newc);
        center= newc.copy();
    }
    public PVector changeCenterNewPosition(PVector newc){
        PVector dif= new PVector(center.x-newc.x,center.y-newc.y);
        return new PVector(position.x-dif.x*(cos(angle)*scale-1)+dif.y*sin(angle)*scale,position.y-dif.y*(cos(angle)*scale-1)-dif.x*sin(angle)*scale);
    }

    //{x,y,1}.{{1,0,0},{0,1,0},{-p,-P,1}}.{{s,0,0},{0,s,0},{0,0,1}}.{{cos(a),sin(a),0},{-sin(a),cos(a),0},{0,0,1}}.{{1,0,0},{0,1,0},{p+c,P+C,1}}
    //={{s Cos[a], s Sin[a], 0}, {-(s Sin[a]), s Cos[a], 0}, {c + p - p s Cos[a] + P s Sin[a], C + P - P s Cos[a] - p s Sin[a], 1}}
    //{x,y,1}.{{1,0,0},{0,1,0},{-o,-O,1}}.{{k,0,0},{0,k,0},{0,0,1}}.{{cos(b),sin(b),0},{-sin(b),cos(b),0},{0,0,1}}.{{1,0,0},{0,1,0},{o+v,O+V,1}}
    //={{k Cos[b], k Sin[b], 0}, {-(k Sin[b]), k Cos[b], 0}, {o + v - k o Cos[b] + k O Sin[b], O + V - k O Cos[b] - k o Sin[b], 1}}
//    PVector centerDiff=new PVector(p.center.x-center.x,p.center.y-center.y);
    ////        PVector newPos= new PVector(scale*p.position.x+p.center.x-center.x+p.scale*(cos(p.angle)*(scale*position.x+center.x-p.center.x)-sin(p.angle)*(scale*position.y+center.y-p.center.y)),0);
//        PVector newPos= new PVector(p.position.x+centerDiff.x+p.scale*(cos(p.angle)*(-centerDiff.x)-sin(p.angle)*(scale*position.y-centerDiff.y)),0);
//        System.out.println("New Position x:"+newPos.x+" y:"+newPos.y);
//        PVector newCenter= center.copy();
//        return new SimpleMatrix(newPos,newCenter,angle+p.angle,scale*p.scale);

    public PVector applyMatrixNewPosition(SimpleMatrix p){//DEFAULTED TO center 0,0
        PVector thisPos= changeCenterNewPosition(new PVector(0,0));
        PVector pPos= p.changeCenterNewPosition(new PVector(0,0));
        return new PVector(thisPos.x+scale*(cos(angle)*pPos.x-sin(angle)*pPos.y),thisPos.y+scale*(cos(angle)*pPos.y+sin(angle)*pPos.x));
        //PVector(thisPos.x+scale*(cos(angle)*pPos.x-sin(angle)*pPos.y),thisPos.y+scale*(cos(angle)*pPos.y+sin(angle)*pPos.x));
    }
    public SimpleMatrix createApplied(SimpleMatrix p){
        SimpleMatrix applied=new SimpleMatrix(applyMatrixNewPosition(p),new PVector(0,0),angle+p.angle,scale*p.scale);
        applied.changeCenter(p.center.copy());
        return applied;
    }
    public void apply(SimpleMatrix p) {
        position = applyMatrixNewPosition(p);
        angle= angle+p.angle;
        scale= scale*p.scale;
        center=new PVector(0,0);
        changeCenter(p.center.copy());
    }
    public SimpleMatrix createInverse(){
        SimpleMatrix clone= this.clone();
        clone.changeCenter(new PVector(0,0));
//        clone.position= new PVector(-(cos(angle)*clone.position.x-sin(angle)*clone.position.y)/scale,)
        clone.position.rotate(-angle).mult(-1f/scale);
        clone.angle=-angle;
        clone.scale=1f/scale;
        clone.changeCenter(this.center.copy());
        return clone;
    }
    public boolean equals(SimpleMatrix p){
        return p.position.equals(position)&&p.center.equals(center)&&(p.angle==angle)&&(p.scale==scale);
    }

    @Override
    public SimpleMatrix clone() {
        return new SimpleMatrix(position.copy(),center.copy(),angle,scale);
    }

    public static SimpleMatrix MultiplyArray(ArrayList<SimpleMatrix> list){
        SimpleMatrix prod= new SimpleMatrix();
        for(SimpleMatrix s: list){
            prod.apply(s);
        }
        return prod;
    }
}
