package com.timeline.keyframeController;

import com.processing.PAnim;
import com.timeline.Timeline;

// A timeline controller limited to containing Motion keyframes
//Really doesnt add any functionality, just sets the mode
public class MotionKeyframeController extends KeyframeController {
    public MotionKeyframeController(Timeline dParent) {
        super(dParent, PAnim.MODETimelineMotion);
    }
    //todo: SHOULD ONLY HAVE ACCESS TO SYMBOL POSITIONS
    //todo: FIGURE OUT HOW TO ENFORCE CERTAIN CONTROLLER

}
