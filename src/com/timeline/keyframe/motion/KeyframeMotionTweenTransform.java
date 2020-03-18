package com.timeline.keyframe.motion;

import com.timeline.controller.TimelineController;
import com.util.SimpleMatrix;

public class KeyframeMotionTweenTransform extends KeyframeMotion{
    public KeyframeMotionTweenTransform(TimelineController dParentController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dParentController, dFrameStart, dLength, dNewTransform);
    }
    @Override
    public void modifyDetails(int dFrameNum) {
        float lerpFactor= ((float)dFrameNum)/((float)length);
        if(nextKeyframe() instanceof KeyframeMotion){
            KeyframeMotion nextTransform=(KeyframeMotion) nextKeyframe();
            getParentSymbol().applyTransform(newTransform.lerpMatrix(nextTransform.newTransform,lerpFactor).getMatrix());
        }

    }
}
