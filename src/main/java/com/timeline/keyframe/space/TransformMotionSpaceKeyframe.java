package com.timeline.keyframe.space;

import com.timeline.keyframe.motion.SpaceMotionKeyframe;

//TransformMotionSpaceKeyframe: a keyframe space associated with a TransformSpaceMotionKeyframe.
public class TransformMotionSpaceKeyframe extends MotionSpaceKeyframe {
    public TransformMotionSpaceKeyframe(int dFrameStart, SpaceMotionKeyframe dTransformer) {
        super(dFrameStart, dTransformer);
    }
    @Override
    public void resolveNewSpace() {
//        newSpace= prevSpaceKeyframe.getNewSpace().createApplied(transformKeyframe.getSpaceTransform());
        newSpace=transformKeyframe.getNewSpaceMatrix().clone();
    }
}
