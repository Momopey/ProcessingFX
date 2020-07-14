package com.symbol;

import processing.core.PGraphics;
import processing.core.PVector;

import java.awt.*;
//Symbol parent: A class for symbols that have associated childContainers.
public class ParentSymbol extends Symbol{
    //    public ArrayList<Symbol> children;
    public SymbolContainer symbolContainer;
    public ParentSymbol(PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, SymbolContainer dChildContainer){
        super(dPos,dCenter,dAngle,dScale,dAlpha);
        symbolContainer =dChildContainer;
        symbolContainer.setParentSymbol(this);
    }
    public ParentSymbol(PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha){
        super(dPos,dCenter,dAngle,dScale,dAlpha);
        symbolContainer =new SymbolContainer();
        symbolContainer.setParentSymbol(this);
    }

    public SymbolContainer getSymbolContainer(){
        return symbolContainer;
    }
//    public SymbolParent(RendererInfo dRendererInfo,PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha, SymbolContainer dChildContainer){
////        super(dRendererInfo,dPos,dCenter,dAngle,dScale,dAlpha);
////        childContainer=dChildContainer;
////        childContainer.setParentSymbol(this);
////    }
    public void draw(PGraphics graphic){
        sceneDraw(graphic);
        symbolContainer.render(graphic);
//        super.draw(graphic);
    }
    public void sceneDraw(PGraphics graphic){
        stroke(graphic,Color.BLACK);
        graphic.strokeWeight(2);
        graphic.noFill();
        graphic.rect(1,1, graphic.width-1, graphic.height-1);
        graphic.fill(0);
        graphic.rect(10,10,10,10);
    }
    @Override
    public void fixSymbol(){
        timeline.fixTimeline();
        symbolContainer.fixContainer(this);
    }
    @Override
    public void setTint(Color tint){
        super.setTint(tint);
        fixTint();
    }
    @Override
    public void fixTint(){
        super.fixTint();
        symbolContainer.fixTint();
    }
}
