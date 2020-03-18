package com.timeline.controller;

import com.processing.PAnim;
import com.timeline.Timeline;
import com.timeline.keyframe.Keyframe;

import java.util.ArrayList;

public class TimelineControllerMotion extends TimelineController{
    public TimelineControllerMotion(Timeline dParent, ArrayList<Keyframe> dKeyFrames) {
        super(dParent, PAnim.MODETimelineMotion, dKeyFrames);
    }
    //todo: SHOULD ONLY HAVE ACCESS TO SYMBOL POSITIONS
    //todo: FIGURE OUT HOW TO ENFORCE CERTAIN CONTROLLER

}
