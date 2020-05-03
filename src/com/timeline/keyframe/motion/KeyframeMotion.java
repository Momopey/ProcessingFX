package com.timeline.keyframe.motion;

import com.processing.PAnim;
import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

// KeyframeMotion: A class for motion keyframes that apply transformations to the parent symbol
public class KeyframeMotion extends Keyframe {
    protected SimpleMatrix newTransform;

    public SimpleMatrix getNewTransform() {
        return newTransform;
    }

    public Keyframe setNewTransform(SimpleMatrix newTransform) {
        this.newTransform = newTransform;
        return this;
    }

    public KeyframeMotion(int dFrameStart, int dLength){
        super(PAnim.MODETimelineMotion, dFrameStart, dLength);
    }
    public KeyframeMotion( int dFrameStart, int dLength,SimpleMatrix dNewTransform) {
        super(PAnim.MODETimelineMotion, dFrameStart, dLength);
        newTransform= dNewTransform;
    }
    public KeyframeMotion(TimelineController dController, int dFrameStart, int dLength,SimpleMatrix dNewTransform) {
        super(PAnim.MODETimelineMotion,dController, dFrameStart, dLength);
        newTransform= dNewTransform;
    }
    public SimpleMatrix getTransformAt(int dFrameNum){
        return newTransform;
    }
    public void inFrameModifyDetails(int dFrameNum) {
        getParentSymbol().applyTransform(getTransformAt(dFrameNum));
    }
    public void requestResolveRelationship(Keyframe requester) {
        if(requester instanceof KeyframeMotionSpace){
            addDependentResolveRelationship(requester);
        }
    }
    public void removeResolveRelationship(Keyframe requester){
        if(requester instanceof KeyframeMotionSpace){
            removeDependentResolveKeyframe(requester);
        }
    }
}
