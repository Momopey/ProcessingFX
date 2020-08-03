package com.util.canvas.actions.nondraw.stroke;

import com.util.canvas.CanvasAction;
import processing.core.PGraphics;

public class StrokeWeightCanvasAction implements CanvasAction {
    float weight;
    public StrokeWeightCanvasAction(float weight) {
        this.weight = weight;
    }

    @Override
    public void Run(PGraphics graphic) {
        graphic.strokeWeight(weight);
    }
}
