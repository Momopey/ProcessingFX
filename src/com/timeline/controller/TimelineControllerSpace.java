package com.timeline.controller;

import com.processing.PAnim;
import com.timeline.Timeline;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.space.KeyframeSpace;
import com.util.SimpleMatrix;

import java.util.ArrayList;

//TimelineControllerSpace: A timeline controller limited to space keyframes. Every symbol is preequipped with one of these.
// It has some special functionality regarding the addition of new keyframes, insuring every space keyframe is filling, and knows its neigbors.
public class TimelineControllerSpace extends TimelineController{
    public TimelineControllerSpace(Timeline dParent) {
        super(dParent, PAnim.MODETimelineSpace);
    }

    @Override
    public void addKeyFrame(Keyframe newKeyframe){
        newKeyframe.setParentController(this);
//
        ArrayList<Keyframe> KeyframeOn= getKeyframesContaining(newKeyframe.getFrameStart());
        if(KeyframeOn.size()==0){
            System.out.println("Timeline cotroller space is messed up, not everything is full, or something else");
            return;
        }

        if(!(newKeyframe instanceof KeyframeSpace)){
            System.out.println("Timeline cotroller space is messed up, keyframe not instace of keyframeSpace");
            return;
        }
        KeyframeSpace newkeyframe= (KeyframeSpace) newKeyframe;
        if(!(KeyframeOn.get(0) instanceof KeyframeSpace)){
            System.out.println("Timeline cotroller space is messed up, keyframeon not instace of keyframeSpace");
            return;
        }
        KeyframeSpace keyframe= (KeyframeSpace) KeyframeOn.get(KeyframeOn.size()-1);
//        newkeyframe.applyToTransform(keyframe.getNewSpace());
        newkeyframe.combineWithKeyframe(keyframe);
////        newKeyframe.addRequiredResolveKeyframe(keyframe); now  part of the combine with keyframe
        //I just removed the previous frame removal deliniation: check if this becomes a problem later
        super.addKeyFrame(newKeyframe);
        keyframe.resolveKeyframe();
    }

    public void SetDefaultSpace(SimpleMatrix defaultSpace){
        KeyframeSpace firstframe= new KeyframeSpace(0,defaultSpace);
        firstframe.setName("Default Space");
        firstframe.setParentController(this);
//        firstframe.resolveKeyframe();
        keyframes.add(firstframe);
    }
}
