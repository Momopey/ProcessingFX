package com.timeline.keyframe.space;

import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.KeyframeMotionSpace;
import com.util.SimpleMatrix;

//KeyframeSpace: Keyframe containing a space matrix
public class KeyframeSpace extends Keyframe {
    protected SimpleMatrix newSpace;
    protected KeyframeSpace nextKeyframeSpace;

    public SimpleMatrix getNewSpace() {
        return newSpace;
    }

    public Keyframe setNewSpace(SimpleMatrix newSpace) {
        this.newSpace = newSpace;
        return this;
    }

    public KeyframeSpace(int dFrameStart) {
        super(PAnim.MODETimelineSpace, dFrameStart, 1);
    }
    public KeyframeSpace(int dFrameStart, SimpleMatrix dSpace) {
        super(PAnim.MODETimelineSpace, dFrameStart, 1);
        newSpace=dSpace;
    }
    public KeyframeSpace(Mode dMode, TimelineController dParentController, int dFrameStart,SimpleMatrix dSpace) {
        super(dMode, dParentController, dFrameStart,1);
        newSpace=dSpace;
    }

    public void applyTransform(SimpleMatrix trans){
        newSpace= newSpace.createApplied(trans);
//                apply(trans);
    }
    public void applyToTransform(SimpleMatrix trans){
        newSpace= trans.createApplied(newSpace);
    }
    public void combineWithKeyframe(KeyframeSpace prevSpace){};


    @Override
    public void inFrameModifyDetails(int dFrameNum) {
        getParentSymbol().setSpaceMatrix(newSpace);
//
    }
    @Override
    public void requestResolveRelationship(Keyframe requester){
        if(requester instanceof KeyframeSpace){
            addDependentResolveRelationship(requester);
            nextKeyframeSpace= (KeyframeSpace) requester;
            System.out.println("Space keyframe("+Name+") adding a depentent space keyframe("+requester.getName()+")");
        }
    }
    @Override
    public void removeResolveRelationship(Keyframe requester){
        if(requester instanceof KeyframeSpace){
            removeDependentResolveRelationship(requester);
            System.out.println("Space keyframe("+Name+") removing a depentent space keyframe("+requester.getName()+")");
        }
        if(requester instanceof KeyframeMotionSpace){
            removeDependentResolveKeyframe(requester);
        }
    }
    @Override
    public void checkKeyframeResolve() {
        resolved=false;
        lengthenToNextKeyframe();//Maybe a bad idea but i dont think so
        Keyframe nextKeyframe = nextKeyframe();
        if (nextKeyframe != null) {
            if (nextKeyframe instanceof KeyframeSpace) {
                if (nextKeyframe != nextKeyframeSpace) {
                    removeResolveRelationship(nextKeyframeSpace);
                    nextKeyframeSpace = (KeyframeSpace) nextKeyframe;
                    requestResolveRelationship(nextKeyframe);
//                    nextKeyframe.deResolve();
                }
            } else {
                System.out.println("KeyframeSpace next keyframe not instance of KeyframeSpace");
            }
        }else{
            nextKeyframeSpace=null;
        }
    }
    @Override
    public void resolveKeyframe(){
        Keyframe nextKeyframe = nextKeyframe();
        if (nextKeyframe != null) {
            if (nextKeyframe instanceof KeyframeSpace) {
                if (nextKeyframe != nextKeyframeSpace) {
                    removeResolveRelationship(nextKeyframeSpace);
                    nextKeyframeSpace = (KeyframeSpace) nextKeyframe;
                    requestResolveRelationship(nextKeyframe);
//                    nextKeyframe.deResolve();
                }
            } else {
                System.out.println("KeyframeSpace next keyframe not instance of KeyframeSpace");
            }
        }else{
            nextKeyframeSpace=null;
        }
        lengthenToNextKeyframe();
        super.resolveKeyframe();
//        System.out.println();
    }
    @Override
    public String getDebugInfo(){
        if(nextKeyframeSpace!=null) {
            return super.getDebugInfo() + " nextKeyframe:(" + nextKeyframeSpace.Name + ") ";
        }
        return  super.getDebugInfo();
    }
}
