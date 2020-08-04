package com.timeline.keyframe.motion;

import com.mode.DataMode;
import com.processing.PAnim;
import com.timeline.keyframe.data.DataModifierKeyframe;
import com.timeline.keyframe.data.space.SpaceKeyframe;
import com.timeline.keyframeController.KeyframeController;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

// StaticTransformMotionKeyframe: simple transformation.
public class StaticTransformMotionKeyframe extends MotionKeyframe  {
    public StaticTransformMotionKeyframe(int dFrameStart, int dLength) {
        super(dFrameStart, dLength);
    }
    public StaticTransformMotionKeyframe(KeyframeController dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
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
