package com.timeline.keyframe.motion;

import com.timeline.Timeline;
import com.timeline.controller.TimelineController;
import com.util.SimpleMatrix;

//Tween transformation: lerps between this and the next keyframe motion (it needs to be a keyframemotion)
public class KeyframeMotionTransformTween extends KeyframeMotion{
    public KeyframeMotionTransformTween(TimelineController dController, int dFrameStart, SimpleMatrix dNewTransform) {
        super(dController,dFrameStart, 1, dNewTransform);
    }
    public KeyframeMotionTransformTween(int dFrameStart) {
        super(dFrameStart, 1);

    }
    public KeyframeMotionTransformTween(int dFrameStart, SimpleMatrix dNewTransform) {
        super(dFrameStart, 1, dNewTransform);

    }
//    @Override
//    public void inFrameModifyDetails(int dFrameNum) {
//
//    }

    @Override
    public SimpleMatrix getTransformAt(int dFrameNum){
        float lerpFactor= ((float)dFrameNum)/((float)length);
        if(nextKeyframe() instanceof KeyframeMotion){
            KeyframeMotion nextTransform=(KeyframeMotion) nextKeyframe();
            if(nextTransform.getFrameStart()>=frameStart) {
                return newTransform.lerpMatrix(nextTransform.newTransform, lerpFactor);
            }
        }
        System.out.println("Next keyframe not a motion keyframe");
        return new SimpleMatrix();
    }
    @Override
    public void resolveKeyframe(){
        lengthenToNextKeyframe();
        System.out.println("("+Name+" )New length:"+length);
        if(nextKeyframe()!=null) {
//            addRequiredResolveKeyframe(nextKeyframe());
            nextKeyframe().requestResolveRelationship(this);
        }
        super.resolveKeyframe();
//        System.out.println("fixing the thing");
    }
}
