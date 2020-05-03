package com.timeline.keyframe.space;

import com.timeline.keyframe.motion.KeyframeMotionSpace;
import com.util.SimpleMatrix;

//KeyframeSpaceMotionTransform: a keyframe space associated with a KeyframeMotionSpaceTransform.
public class KeyframeSpaceMotionTransform extends KeyframeSpaceMotion {
    public KeyframeSpaceMotionTransform(int dFrameStart, KeyframeMotionSpace dTransformer) {
        super(dFrameStart, dTransformer);
    }
    @Override
    public void resolveNewSpace() {
//        newSpace= prevKeyframeSpace.getNewSpace().createApplied(transformKeyframe.getSpaceTransform());
        newSpace=transformKeyframe.getNewSpaceMatrix().clone();
    }
}
