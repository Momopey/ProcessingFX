package com.symbol;

import processing.core.PGraphics;
import processing.core.PVector;

import java.awt.*;

public class SymbolParent extends Symbol{
    //    public ArrayList<Symbol> children;
public ChildrenContainer childContainer;
    public SymbolParent(PVector dPos, PVector dCenter, float dAngle, float dScale, float dAlpha,ChildrenContainer dChildContainer){
        super(dPos,dCenter,dAngle,dScale,dAlpha);
        childContainer=dChildContainer;
        childContainer.setParentSymbol(this);
    }
    public void draw(PGraphics graphic){
        sceneDraw(graphic);
        childContainer.render(graphic);
//        super.draw(graphic);
    }
    public void sceneDraw(PGraphics graphic){
        stroke(graphic,new Color(0,0,0));
        graphic.noFill();
        graphic.rect(0,0, graphic.width, graphic.height);
    }
}
