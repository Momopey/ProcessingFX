package com.timeline.keyframe.motion;

import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

public class KeyframeMotionStaticTransform extends KeyframeMotion {
    public KeyframeMotionStaticTransform( TimelineController dParentController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dParentController, dFrameStart, dLength, dNewTransform);
    }
    @Override
    public void modifyDetails(int dFrameNum) {
        getParentSymbol().applyTransform(newTransform.getMatrix());
    }
}
