package com.timeline.keyframe.motion;

import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

// StaticTransformMotionKeyframe: simple transformation.
public class StaticTransformMotionKeyframe extends MotionKeyframe {
    public StaticTransformMotionKeyframe(int dFrameStart, int dLength) {
        super(dFrameStart, dLength);
    }
    public StaticTransformMotionKeyframe(KeyframeContainer dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dController, dFrameStart, dLength, dNewTransform);
    }
    public StaticTransformMotionKeyframe(int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super( dFrameStart, dLength, dNewTransform);
    }

//    @Override
//    public void resolveKeyframe(){
//        resolved=true;
//    }
    @Override
    public void requestResolveRelationship(Keyframe requester) {
        addDependentResolveRelationship(requester);
    }

    @Override
    public StaticTransformMotionKeyframe setName(String newName) {
        Name=newName;
        return this;
    }
}
