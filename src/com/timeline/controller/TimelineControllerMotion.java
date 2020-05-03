package com.timeline.controller;

import com.processing.PAnim;
import com.timeline.Timeline;
import com.timeline.keyframe.Keyframe;

import java.util.ArrayList;
// A timeline controller limited to containing Motion keyframes
//Really doesnt add any functionality, just sets the mode
public class TimelineControllerMotion extends TimelineController{
    public TimelineControllerMotion(Timeline dParent) {
        super(dParent, PAnim.MODETimelineMotion);
    }
    //todo: SHOULD ONLY HAVE ACCESS TO SYMBOL POSITIONS
    //todo: FIGURE OUT HOW TO ENFORCE CERTAIN CONTROLLER

}
