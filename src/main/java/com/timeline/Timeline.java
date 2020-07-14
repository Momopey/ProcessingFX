package com.timeline;

import com.symbol.Symbol;
import com.symbol.ParentSymbol;
import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframeContainer.SpaceKeyframeContainer;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
// Timeline: A class for timelines associated with a symbol. Act as a container for timeline controllers.
public class Timeline{
    public int frameStart;
    public int length;
    public Symbol parentSymbol;

    public SpaceKeyframeContainer spaceController;
    public ArrayList<KeyframeContainer> controllers;
    private int frameNumber;

    public Timeline(Symbol dParentSymbol){
        parentSymbol= dParentSymbol;
        spaceController=new SpaceKeyframeContainer(this);
        controllers= new ArrayList<KeyframeContainer>();
        controllers.add(spaceController);
    }
    public void setSize(int dFrameStart,int dLength){
        frameStart=dFrameStart;
        length=dLength;
    }
    
    public void addTimelineController(KeyframeContainer newController){
//        if(parentSymbol.usesMode(newController.mode)){
            newController.setParentTimeline(this);
            controllers.add(newController);
//        }
    }
    public void fixTimeline(){
        for( KeyframeContainer c : controllers){
            c.fixController();
        }
    }
    public void loadFrame(int dFrameNumber){// Framenumber relative to start of scene

        frameNumber=dFrameNumber-frameStart;//Select internal frame
        if(frameNumber<0){return;}// If outside of timelinerange, do nothing (Maybe hide render later)
        if(frameNumber>length){return;}
        parentSymbol.refreshDefault();

        for(int contnum=0;contnum<controllers.size();contnum++){//first check resolvement
            KeyframeContainer thiscontroller= controllers.get(contnum);
            if(parentSymbol.usesMode(thiscontroller.getMode())){
                thiscontroller.checkResolvement(frameNumber);
            }
        }
        for(int contnum=0;contnum<controllers.size();contnum++){//loop through
            KeyframeContainer thiscontroller= controllers.get(contnum);
            if(parentSymbol.usesMode(thiscontroller.getMode())){
                thiscontroller.LoadKeyframe(frameNumber);
            }
        }

        if(parentSymbol instanceof ParentSymbol){// If the symbol has children
            ParentSymbol parentScene= (ParentSymbol)parentSymbol;// Cast to scene
            parentScene.symbolContainer.loadFrame(frameNumber,parentScene);
        }
    }
    public void drawTimelineInEditor(GraphicsContext editorGC){
        for(KeyframeContainer tc:controllers){
            tc.drawKeyframesInTimeline(editorGC);
        }
    }
}
