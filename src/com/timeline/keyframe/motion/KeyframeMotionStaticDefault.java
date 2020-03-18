package com.timeline.keyframe.motion;

import com.timeline.controller.TimelineController;
import com.util.SimpleMatrix;

public class KeyframeMotionStaticDefault extends KeyframeMotion{
    SimpleMatrix newDefaultPosition;
    public KeyframeMotionStaticDefault(TimelineController dParentController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dParentController, dFrameStart, dLength, dNewTransform);
    }
    @Override
    public void modifyDetails(int dFrameNum) {
        if(newDefaultPosition==null){
            newDefaultPosition= getParentSymbol().getTransformDefault().apply(newTransform);
            getParentSymbol().setTransform(newDefaultPosition);
        }else if(dFrameNum==0){
            getParentSymbol().setTransform(newDefaultPosition);
        }
    }
}
