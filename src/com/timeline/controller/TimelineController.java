package com.timeline.controller;

import com.mode.Mode;
import com.timeline.keyframe.Keyframe;
import com.timeline.Timeline;

import java.util.ArrayList;

public class TimelineController{
    public Timeline parentTimeline;
    public Mode mode;
    public ArrayList<Keyframe> keyframes;
    public TimelineController(Timeline dParent, Mode dMode,ArrayList<Keyframe> dKeyFrames){
        parentTimeline= dParent; mode=dMode;
        keyframes=dKeyFrames;
    }
    public void modifyDetails(int dFrameNumber){//framenumber relatve to start os scene
        for(int keyframenum=0;keyframenum<keyframes.size();keyframenum++){
            Keyframe thiskeyframe= keyframes.get(keyframenum);
            int frameNum=dFrameNumber-thiskeyframe.frameStart;
            if(frameNum>=0 && frameNum<thiskeyframe.length) {
                thiskeyframe.modifyDetails(frameNum);
            }
        }
    }
}
