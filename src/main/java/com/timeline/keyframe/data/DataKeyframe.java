package com.timeline.keyframe.data;

import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.SpaceMotionKeyframe;
import com.timeline.keyframeContainer.KeyframeController;
import com.util.SimpleMatrix;

//SpaceKeyframe: Keyframe containing a space matrix
public abstract class DataKeyframe extends Keyframe {
    protected boolean historyDependent;
    protected DataKeyframe nextDataKeyframe;

    public DataKeyframe(Mode dMode, KeyframeController dParentController, int dFrameStart) {
        super(dMode, dParentController, dFrameStart,1);
    }
    public DataKeyframe(Mode dMode, int dFrameStart) {
        super(dMode, dFrameStart,1);
    }

    public void combineWithKeyframe(DataKeyframe prevSpace){};

    @Override
    public void requestResolveRelationship(Keyframe requester){
        if(getClass().isInstance(requester)){
            addDependentResolveRelationship(requester);
            nextDataKeyframe = getClass().cast(requester);
            System.out.println("Space keyframe("+Name+") adding a depentent space keyframe("+requester.getName()+")");
        }
    }
    @Override
    public void removeResolveRelationship(Keyframe requester){
        if(requester instanceof DataKeyframe){
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
        loadNextKeyframe();
    }
    @Override
    public void resolveKeyframe(){
        loadNextKeyframe();
        lengthenToNextKeyframe();
        super.resolveKeyframe();
//        System.out.println();
    }
    @Override
    public String getDebugInfo(){
        if(nextDataKeyframe !=null) {
            return super.getDebugInfo() + " nextKeyframe:(" + nextDataKeyframe.Name + ") ";
        }
        return  super.getDebugInfo();
    }
    protected void loadNextKeyframe(){
        Keyframe nextKeyframe = nextKeyframe();
//        ((SpaceKeyframeController) getParentController()).testFix(this);
        if (nextKeyframe != null) {
            if (getClass().isInstance(nextKeyframe)) {

                if (nextKeyframe != nextDataKeyframe) {
                    removeResolveRelationship(nextDataKeyframe);
                    nextDataKeyframe =getClass().cast(nextKeyframe);
                    requestResolveRelationship(nextKeyframe);
//                    nextKeyframe.deResolve();
                }
            } else {
                System.out.println("SpaceKeyframe next keyframe not instance of SpaceKeyframe");
            }
        }else{
            nextDataKeyframe =null;
        }
    }
    @Override
    public DataKeyframe setName(String newName) {
        Name=newName;
        return this;
    }
}
