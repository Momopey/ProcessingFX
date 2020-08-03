package com.util.canvas.actions.nondraw.stroke;

import com.util.canvas.CanvasAction;
import processing.core.PGraphics;

public class StrokeJoinCanvasAction implements CanvasAction {
    int join;
    public StrokeJoinCanvasAction(int dJoin){
        join=dJoin;
    }
    @Override
    public void Run(PGraphics graphic) {
        graphic.strokeJoin(join);
    }
}
