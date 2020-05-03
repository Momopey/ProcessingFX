package com.timeline.keyframe.motion;

import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

// KeyframeMotionTransformStatic: simple transformation.
public class KeyframeMotionTransformStatic extends KeyframeMotion {
    public KeyframeMotionTransformStatic( int dFrameStart, int dLength) {
        super(dFrameStart, dLength);
    }
    public KeyframeMotionTransformStatic(TimelineController dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dController, dFrameStart, dLength, dNewTransform);
    }
    public KeyframeMotionTransformStatic(int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
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
}
