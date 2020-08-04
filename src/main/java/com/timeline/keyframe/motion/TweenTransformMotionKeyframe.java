package com.timeline.keyframe.motion;

import com.timeline.keyframeController.KeyframeController;
import com.util.SimpleMatrix;

//Tween transformation: lerps between this and the next keyframe motion (it needs to be a keyframemotion)
public class TweenTransformMotionKeyframe extends MotionKeyframe {
    public TweenTransformMotionKeyframe(KeyframeController dController, int dFrameStart, SimpleMatrix dNewTransform) {
        super(dController,dFrameStart, 1, dNewTransform);
    }
    public TweenTransformMotionKeyframe(int dFrameStart) {
        super(dFrameStart, 1);

    }
    public TweenTransformMotionKeyframe(int dFrameStart, SimpleMatrix dNewTransform) {
        super(dFrameStart, 1, dNewTransform);

    }
//    @Override
//    public void inFrameModifyDetails(int dFrameNum) {
//
//    }

    @Override
    public SimpleMatrix getTransformAt(int dFrameNum){
        float lerpFactor= ((float)dFrameNum)/((float)length);
        if(nextKeyframe() instanceof MotionKeyframe){
            MotionKeyframe nextTransform=(MotionKeyframe) nextKeyframe();
            if(nextTransform.getFrameStart()>=frameStart) {
                return newTransform.lerpMatrix(nextTransform.newTransform, lerpFactor);
            }
        }
        System.out.println("Next keyframe not a motion keyframe");
        return new SimpleMatrix();
    }
    @Override
    public void resolve(){
        lengthenToNextKeyframe();
        System.out.println("("+Name+" )New length:"+length);
        if(nextKeyframe()!=null) {
//            addRequiredResolveKeyframe(nextKeyframe());
            nextKeyframe().requestResolveRelationship(this);
        }
        super.resolve();
//        System.out.println("fixing the thing");
    }
    @Override
    public TweenTransformMotionKeyframe setName(String newName) {
        Name=newName;
        return this;
    }
}
