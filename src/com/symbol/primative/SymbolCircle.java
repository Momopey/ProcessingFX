package com.symbol.primative;

import com.appsystem.RendererInfo;
import com.symbol.ChildrenContainer;
import jogamp.opengl.awt.Java2D;
import processing.core.PGraphics;
import processing.core.PVector;
import java.awt.*;

import static processing.core.PConstants.JAVA2D;
import static processing.core.PConstants.P2D;

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
        fill(graphic,fillColor);
        stroke(graphic,strokeColor);
        graphic.strokeWeight(strokeWeight);
        graphic.ellipse(0, 0, diameter,diameter);
    }
}
