package com.symbol.primative;

import com.symbol.ChildrenContainer;
import processing.core.PGraphics;
import processing.core.PVector;
import java.awt.*;
public class SymbolCircle extends SymbolPrim {
    protected float diameter;
    protected Color fillColor;
    protected Color strokeColor;
    protected float strokeWeight;
    public SymbolCircle(ChildrenContainer dParent, PVector dPos, float dDiam, float dStrokeWeight, Color dFill, Color dStroke, float dAlpha) {
        super(dParent,dPos, new PVector(0, 0), 0,1, dAlpha);
        diameter = dDiam;
        strokeWeight= dStrokeWeight;
        fillColor=dFill;
        strokeColor=dStroke;
    }
    public void draw(PGraphics graphic) {
        fill(graphic,fillColor,alphaMult);
        stroke(graphic,strokeColor,alphaMult);
        graphic.strokeWeight(strokeWeight);
        graphic.ellipse(0, 0, diameter,diameter);
    }
}
