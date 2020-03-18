package com.timeline.keyframe.motion;

import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;
import processing.core.PVector;

public class KeyframeMotion extends Keyframe {
    protected SimpleMatrix newTransform;
    public KeyframeMotion(TimelineController dParentController, int dFrameStart, int dLength,SimpleMatrix dNewTransform) {
        super(PAnim.MODETimelineMotion, dParentController, dFrameStart, dLength);
        newTransform= dNewTransform;
    }
}
