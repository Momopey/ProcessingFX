package com.symbol.usermade;

import com.processing.PAnim;
import com.symbol.ChildrenContainer;
import com.symbol.primative.SymbolCircle;
import processing.core.PVector;

import java.awt.*;

public class Circle2 extends SymbolCircle {
    PVector velocity;
    public Circle2(ChildrenContainer dParent, PVector dPos, PVector dVel, float dDiam){
        super(dParent,dPos,dDiam,5, new Color(255,255,255,255),new Color(0,0,0,255),1);
        velocity=dVel;

    }
    public void update(){
//        position=position.add(velocity);
//        float bouncefrict=0.9f;
//        velocity=velocity.add(new PVector(0,0.5f));
//        if(position.y<diameter/2){
//            velocity= new PVector(velocity.x,Math.abs(velocity.y)*bouncefrict);
//        }
//        if(position.y> PAnim.processing.graphicsWindow.height-diameter/2){
//            velocity= new PVector(velocity.x,-Math.abs(velocity.y)*bouncefrict);
//        }
//        if(position.x<diameter/2){
//            velocity= new PVector(Math.abs(velocity.x)*bouncefrict,velocity.y);
//        }
//        if(position.x> PAnim.processing.graphicsWindow.width-diameter/2){
//            velocity= new PVector(-Math.abs(velocity.x)*bouncefrict,velocity.y);
//        }
    }

}