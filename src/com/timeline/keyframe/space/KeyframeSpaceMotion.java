package com.timeline.keyframe.space;

import com.mode.Mode;
import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.KeyframeMotionSpace;
import com.util.SimpleMatrix;

//KeyframeSpaceMotion: keyframeSpace with connection to a keyframemotionspace keyframe
public abstract class KeyframeSpaceMotion extends KeyframeSpace{
    protected KeyframeMotionSpace transformKeyframe;
    protected KeyframeSpace prevKeyframeSpace;

    public KeyframeSpaceMotion(int dFrameStart, KeyframeMotionSpace dTransformer) {
        super(dFrameStart, new SimpleMatrix());
        transformKeyframe=dTransformer;
    }

    public KeyframeSpaceMotion(Mode dMode, TimelineController dParentController, int dFrameStart, SimpleMatrix dSpace) {
        super(dMode, dParentController, dFrameStart, dSpace);
    }
    public KeyframeSpace getPrevKeyframeSpace() {
        return prevKeyframeSpace;
    }

    @Override
    public void combineWithKeyframe(KeyframeSpace dPrevSpace){
        if(prevKeyframeSpace ==null) {
//            dPrevSpace.deResolve();
            dPrevSpace.lengthenToNextKeyframe();
            prevKeyframeSpace = dPrevSpace;
//        applyToTransform(prevSpace.getNewSpace());
//        newSpace=prevSpace.getNewSpace().createApplied(transformKeyframe.getSpaceTransform());
            prevKeyframeSpace.requestResolveRelationship(this);
        }
    };

    @Override
    public void requestResolveRelationship(Keyframe requester){
        super.requestResolveRelationship(requester);
        if(requester instanceof KeyframeMotionSpace){
            addDependentResolveRelationship(requester);
            addRequiredResolveRelationship(requester);
//            System.out.println("Space keyframe adding a depentent keyframe motion space transform");
        }
    }
    @Override
    public void removeResolveRelationship(Keyframe requester){
        super.removeResolveRelationship(requester);
        if(requester instanceof KeyframeMotionSpace){
            removeDependentResolveKeyframe(requester);
            removeRequiredResolveKeyframe(requester);
            System.out.println("WTF REMOVING RESOLVE WITH K EYFRAME MOTIONSPACETRANSFRTMWTF REMOVING RESOLVE WITH KEYFRAME MOTIONSPACETRANSFRTMWTF REMOVING RESOLVE WITH KEYFRAME MOTIONSPACETRANSFRTM");
//            System.out. rintln("Space keyframe adding a depentent keyframe motion space transform");
        }
    }


    @Override
    public void checkKeyframeResolve(){
        resolved=false;
        if(frameStart!=transformKeyframe.getFrameStart()){
            frameStart=transformKeyframe.getFrameStart();
            fixKeyframeIndex();
        }
        Keyframe prevKeyframe = getPrevKeyframe();
        if(prevKeyframe==null){
            System.out.println("Keyframe Space Transform previous keyframe does not exist");
        }else{
            if(prevKeyframe instanceof  KeyframeSpace) {
                if (prevKeyframe != prevKeyframeSpace) {
                    prevKeyframeSpace.removeResolveRelationship(this);
                    prevKeyframeSpace = (KeyframeSpace)prevKeyframe;
                    prevKeyframeSpace.requestResolveRelationship(this);
//                    prevKeyframe.checkResolve();
                }
            }
        }
        prevKeyframeSpace.lengthenToNextKeyframe();
        super.checkKeyframeResolve();

    }
    @Override
    public void resolveKeyframe(){
//        System.out.println("");
//        if(frameStart!=transformKeyframe.getFrameStart()){
//            frameStart=transformKeyframe.getFrameStart();
//            fixKeyframeIndex();
//        }

//        Keyframe prevKeyframe = getPrevKeyframe();
//        if(prevKeyframe==null){
//            System.out.println("Keyframe Space Transform previous keyframe does not exist");
//        }else{
//            if(prevKeyframe instanceof  KeyframeSpace) {
//                if (prevKeyframe != prevKeyframeSpace) {
//                    prevKeyframeSpace.removeResolveRelationship(this);
//                    prevKeyframeSpace = (KeyframeSpace)prevKeyframe;
//                    prevKeyframeSpace.requestResolveRelationship(this);
////                    prevKeyframe.checkResolve();
//                }
//            }
//        }
//        prevKeyframeSpace.lengthenToNextKeyframe();
        resolveRequiredKeyframes();
        System.out.println("("+Name+") framestart:"+frameStart+"  length:"+length +"PrevKeyframeSpace:("+prevKeyframeSpace.getName()+")");
//        System.out.println("Prev space:"+prevSpace+" "+prevSpace.frameStart+ " "+prevSpace.getNewSpace().getPosition().x+" "+prevSpace.getNewSpace().getPosition().y);
        resolveNewSpace();
    }
    public void resolveRequiredKeyframes(){
        checkKeyframeResolve();
        super.resolveKeyframe();
    }
    public abstract void resolveNewSpace();//
    @Override
    public String getDebugInfo(){
        if(prevKeyframeSpace!=null) {
            return super.getDebugInfo() + " prevKeyframe:(" + prevKeyframeSpace.getName() + ") ";
        }
        return  super.getDebugInfo();
    }

}
