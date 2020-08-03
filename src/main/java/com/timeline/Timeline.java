package com.timeline;

import com.processing.PAnim;
import com.symbol.Symbol;
import com.symbol.ParentSymbol;
import com.timeline.keyframeContainer.KeyframeController;
import com.timeline.keyframeContainer.SpaceKeyframeController;

import java.util.ArrayList;
// Timeline: A class for timelines associated with a symbol. Act as a container for timeline controllers.
public class Timeline{
    public int frameStart;
    public int length;
    public Symbol parentSymbol;

    public SpaceKeyframeController spaceController;
    public ArrayList<KeyframeController> controllers;
    private int frameNumber;

    public Timeline(Symbol dParentSymbol){
        parentSymbol= dParentSymbol;
        spaceController=(SpaceKeyframeController) new SpaceKeyframeController(this, PAnim.MODETimelineSpace).setName("Space Trans.");
        controllers= new ArrayList<KeyframeController>();
        controllers.add(spaceController);
    }
    public void setSize(int dFrameStart,int dLength){
        frameStart=dFrameStart;
        length=dLength;
    }
    
    public void addTimelineController(KeyframeController newController){
//        if(parentSymbol.usesMode(newController.mode)){
            newController.setParentTimeline(this);
            controllers.add(newController);
//        }
    }
    public void fixTimeline(){
        for( KeyframeController c : controllers){
            c.fixController();
        }
    }
    public void loadFrame(int dFrameNumber){// Framenumber relative to start of scene

        frameNumber=dFrameNumber-frameStart;//Select internal frame
        if(frameNumber<0){return;}// If outside of timelinerange, do nothing (Maybe hide render later)
        if(frameNumber>length){return;}
        parentSymbol.refreshDefault();

        for(int contnum=0;contnum<controllers.size();contnum++){//first check resolvement
            KeyframeController thiscontroller= controllers.get(contnum);
            if(parentSymbol.usesMode(thiscontroller.getMode())){
                thiscontroller.checkResolvement(frameNumber);
            }
        }
        for(int contnum=0;contnum<controllers.size();contnum++){//loop through
            KeyframeController thiscontroller= controllers.get(contnum);
            if(parentSymbol.usesMode(thiscontroller.getMode())){
                thiscontroller.LoadKeyframe(frameNumber);
            }
        }

        if(parentSymbol instanceof ParentSymbol){// If the symbol has children
            ParentSymbol parentScene= (ParentSymbol)parentSymbol;// Cast to scene
            parentScene.symbolContainer.loadFrame(frameNumber,parentScene);
        }
    }
}
