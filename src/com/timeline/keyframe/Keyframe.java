package com.timeline.keyframe;

import com.mode.Mode;
import com.symbol.Symbol;
import com.timeline.Timeline;
import com.timeline.controller.TimelineController;

import java.security.Key;

public class Keyframe {
    public int frameStart;
    public int length;
    protected Mode mode;
    protected TimelineController parentController;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public TimelineController getParentController() {
        return parentController;
    }

    public Timeline getParentTimeline(){
        return parentController.parentTimeline;
    }

    public Symbol getParentSymbol(){
        return parentController.parentTimeline.parentSymbol;
    }

    public Keyframe(Mode dMode, TimelineController dParentController, int dFrameStart, int dLength){
        parentController=dParentController;
        frameStart=dFrameStart;
        length=dLength;
        mode=dMode;
        if(mode != parentController.mode){
            System.out.println("Wrong mode for keymode");
        }
    }
    public Keyframe getFollowingKeyframe(int offset){
        int ind= getParentController().keyframes.indexOf(this);
        ind+=offset;
        if(ind<0||ind>=getParentController().keyframes.size()){
            return null;
        }else{
            return getParentController().keyframes.get(ind);
        }
    }
    public Keyframe nextKeyframe(){
        return getFollowingKeyframe(1);
    }
    public Keyframe lengthenToNextKeyframe(){//Thisreturner
        Keyframe next= nextKeyframe();
        if(next != null){
            if(next.frameStart>frameStart){
                length=(next.frameStart-frameStart);
            }
        }
        return this;
    }
    public void modifyDetails(int dFrameNum){};//framenum relatvie to start of keyframe
}
