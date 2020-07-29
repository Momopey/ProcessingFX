package com.timeline.keyframe.space;

import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.keyframe.motion.TweenTransformMotionKeyframe;
import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.SpaceMotionKeyframe;
import com.util.SimpleMatrix;

//SpaceKeyframe: Keyframe containing a space matrix
public class SpaceKeyframe extends Keyframe {
    protected SimpleMatrix newSpace;
    protected SpaceKeyframe nextSpaceKeyframe;

    public SimpleMatrix getNewSpace() {
        return newSpace;
    }

    public Keyframe setNewSpace(SimpleMatrix newSpace) {
        this.newSpace = newSpace;
        return this;
    }

    public SpaceKeyframe(int dFrameStart) {
        super(PAnim.MODETimelineSpace, dFrameStart, 1);
    }
    public SpaceKeyframe(int dFrameStart, SimpleMatrix dSpace) {
        super(PAnim.MODETimelineSpace, dFrameStart, 1);
        newSpace=dSpace;
    }
    public SpaceKeyframe(Mode dMode, KeyframeContainer dParentController, int dFrameStart, SimpleMatrix dSpace) {
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
    public void combineWithKeyframe(SpaceKeyframe prevSpace){};


    @Override
    public void inFrameModifyDetails(int dFrameNum) {
        getParentSymbol().setSpaceMatrix(newSpace);
//
    }
    @Override
    public void requestResolveRelationship(Keyframe requester){
        if(requester instanceof SpaceKeyframe){
            addDependentResolveRelationship(requester);
            nextSpaceKeyframe = (SpaceKeyframe) requester;
            System.out.println("Space keyframe("+Name+") adding a depentent space keyframe("+requester.getName()+")");
        }
    }
    @Override
    public void removeResolveRelationship(Keyframe requester){
        if(requester instanceof SpaceKeyframe){
            removeDependentResolveRelationship(requester);
            System.out.println("Space keyframe("+Name+") removing a depentent space keyframe("+requester.getName()+")");
        }
        if(requester instanceof SpaceMotionKeyframe){
            removeDependentResolveKeyframe(requester);
        }
    }
    @Override
    public void checkKeyframeResolve() {
        resolved=false;
        lengthenToNextKeyframe();//Maybe a bad idea but i dont think so
        Keyframe nextKeyframe = nextKeyframe();
        if (nextKeyframe != null) {
            if (nextKeyframe instanceof SpaceKeyframe) {
                if (nextKeyframe != nextSpaceKeyframe) {
                    removeResolveRelationship(nextSpaceKeyframe);
                    nextSpaceKeyframe = (SpaceKeyframe) nextKeyframe;
                    requestResolveRelationship(nextKeyframe);
//                    nextKeyframe.deResolve();
                }
            } else {
                System.out.println("SpaceKeyframe next keyframe not instance of SpaceKeyframe");
            }
        }else{
            nextSpaceKeyframe =null;
        }
    }
    @Override
    public void resolveKeyframe(){
        Keyframe nextKeyframe = nextKeyframe();
        if (nextKeyframe != null) {
            if (nextKeyframe instanceof SpaceKeyframe) {
                if (nextKeyframe != nextSpaceKeyframe) {
                    removeResolveRelationship(nextSpaceKeyframe);
                    nextSpaceKeyframe = (SpaceKeyframe) nextKeyframe;
                    requestResolveRelationship(nextKeyframe);
//                    nextKeyframe.deResolve();
                }
            } else {
                System.out.println("SpaceKeyframe next keyframe not instance of SpaceKeyframe");
            }
        }else{
            nextSpaceKeyframe =null;
        }
        lengthenToNextKeyframe();
        super.resolveKeyframe();
//        System.out.println();
    }
    @Override
    public String getDebugInfo(){
        if(nextSpaceKeyframe !=null) {
            return super.getDebugInfo() + " nextKeyframe:(" + nextSpaceKeyframe.Name + ") ";
        }
        return  super.getDebugInfo();
    }
    @Override
    public SpaceKeyframe setName(String newName) {
        Name=newName;
        return this;
    }
}
