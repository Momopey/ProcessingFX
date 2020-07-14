package com.symbol.primative;

import com.symbol.CompSymbol;
import com.symbol.ParentSymbol;
import com.symbol.SymbolContainer;
import processing.core.PGraphics;
import processing.core.PVector;
import java.awt.*;

public class CircleSymbol extends CompSymbol{
    protected float diameter;
    protected Color fillColor;
    protected Color strokeColor;
    protected float strokeWeight;
    public CircleSymbol(SymbolContainer dParent, PVector dPos, float dDiam, float dStrokeWeight, Color dFill, Color dStroke, float dAlpha) {
        super(dParent,dPos, new PVector(0, 0), 0,1, dAlpha);
        diameter = dDiam;
        strokeWeight= dStrokeWeight;
        fillColor=dFill;
        strokeColor=dStroke;
    }
    public void draw(PGraphics graphic) {
        fill(graphic,fillColor);
        stroke(graphic,strokeColor);
        graphic.strokeWeight(strokeWeight);
        graphic.ellipse(0, 0, diameter,diameter);
    }
}
