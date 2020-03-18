package com.timeline;

import com.symbol.Symbol;
import com.symbol.SymbolParent;
import com.timeline.controller.TimelineController;

import java.util.ArrayList;

public class Timeline{
    public int frameStart;
    public int length;
    public Symbol parentSymbol;
    public ArrayList<TimelineController> controllers;
    private int frameNumber;

    public Timeline(Symbol dParentSymbol){
        parentSymbol= dParentSymbol;
        controllers= new ArrayList<TimelineController>();
    }
    public void setSize(int dFrameStart,int dLength){
        frameStart=dFrameStart;
        length=dLength;
    }
    
    public void addTimelineController(TimelineController newController){
//        if(parentSymbol.usesMode(newController.mode)){
            newController.parentTimeline=this;
            controllers.add(newController);
//        }
    }
    public void loadFrame(int dFrameNumber){// Framenumber relative to start of scene
        frameNumber=dFrameNumber-frameStart;//Select internal frame
        if(frameNumber<0){return;}// If outside of timelinerange, do nothing (Maybe hide render later)
        if(frameNumber>length){return;}
        parentSymbol.returnToDefault();//Return to default state before loading frame
        for(int contnum=0;contnum<controllers.size();contnum++){//loop through
            TimelineController thiscontroller= controllers.get(contnum);
            if(parentSymbol.usesMode(thiscontroller.mode)){
                thiscontroller.modifyDetails(frameNumber);
            }
        }
        if(parentSymbol instanceof SymbolParent){// If the symbol has children
            SymbolParent parentScene= (SymbolParent)parentSymbol;// Cast to scene
            parentScene.childContainer.loadFrame(frameNumber,parentScene);
        }
    }
}
