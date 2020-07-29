package com.timeline.keyframe.motion;

import com.processing.PAnim;
import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

// MotionKeyframe: A class for motion keyframes that apply transformations to the parent symbol
public class MotionKeyframe extends Keyframe {
    protected SimpleMatrix newTransform;

    public SimpleMatrix getNewTransform() {
        return newTransform;
    }

    public Keyframe setNewTransform(SimpleMatrix newTransform) {
        this.newTransform = newTransform;
        return this;
    }

    public MotionKeyframe(int dFrameStart, int dLength){
        super(PAnim.MODETimelineMotion, dFrameStart, dLength);
        newTransform= new SimpleMatrix();
    }
    public MotionKeyframe(int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(PAnim.MODETimelineMotion, dFrameStart, dLength);
        newTransform= dNewTransform;
    }
    public MotionKeyframe(KeyframeContainer dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
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
        if(requester instanceof SpaceMotionKeyframe){
            addDependentResolveRelationship(requester);
        }
    }
    public void removeResolveRelationship(Keyframe requester){
        if(requester instanceof SpaceMotionKeyframe){
            removeDependentResolveKeyframe(requester);
        }
    }
    @Override
    public MotionKeyframe setName(String newName) {
        Name=newName;
        return this;
    }
}
