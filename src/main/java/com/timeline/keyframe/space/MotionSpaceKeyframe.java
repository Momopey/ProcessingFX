package com.timeline.keyframe.space;

import com.mode.Mode;
import com.timeline.keyframeContainer.KeyframeController;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.SpaceMotionKeyframe;
import com.util.SimpleMatrix;

//MotionSpaceKeyframe: keyframeSpace with connection to a keyframemotionspace keyframe
public class MotionSpaceKeyframe extends SpaceKeyframe {
    protected SpaceMotionKeyframe transformKeyframe;
    protected SpaceKeyframe prevSpaceKeyframe;

    public MotionSpaceKeyframe(int dFrameStart, SpaceMotionKeyframe dTransformer) {
        super(dFrameStart, new SimpleMatrix());
        transformKeyframe=dTransformer;
    }

    public MotionSpaceKeyframe(Mode dMode, KeyframeController dParentController, int dFrameStart, SimpleMatrix dSpace) {
        super(dMode, dParentController, dFrameStart, dSpace);
    }
    public SpaceKeyframe getPrevSpaceKeyframe() {
        return prevSpaceKeyframe;
    }

    @Override
    public void combineWithKeyframe(SpaceKeyframe dPrevSpace){
        if(prevSpaceKeyframe ==null) {
//            dPrevSpace.deResolve();
            dPrevSpace.lengthenToNextKeyframe();
            prevSpaceKeyframe = dPrevSpace;
//        applyToTransform(prevSpace.getNewSpace());
//        newSpace=prevSpace.getNewSpace().createApplied(transformKeyframe.getSpaceTransform());
            prevSpaceKeyframe.requestResolveRelationship(this);
        }
    };

    @Override
    public void requestResolveRelationship(Keyframe requester){
        super.requestResolveRelationship(requester);
        if(requester instanceof SpaceMotionKeyframe){
            addDependentResolveRelationship(requester);
            addRequiredResolveRelationship(requester);
//            System.out.println("Space keyframe adding a depentent keyframe motion space transform");
        }
    }
    @Override
    public void removeResolveRelationship(Keyframe requester){
        super.removeResolveRelationship(requester);
        if(requester instanceof SpaceMotionKeyframe){
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
            if(prevKeyframe instanceof SpaceKeyframe) {
                if (prevKeyframe != prevSpaceKeyframe) {
                    prevSpaceKeyframe.removeResolveRelationship(this);
                    prevSpaceKeyframe = (SpaceKeyframe)prevKeyframe;
                    prevSpaceKeyframe.requestResolveRelationship(this);
//                    prevKeyframe.checkResolve();
                }
            }
        }
        prevSpaceKeyframe.lengthenToNextKeyframe();
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
//            if(prevKeyframe instanceof  SpaceKeyframe) {
//                if (prevKeyframe != prevSpaceKeyframe) {
//                    prevSpaceKeyframe.removeResolveRelationship(this);
//                    prevSpaceKeyframe = (SpaceKeyframe)prevKeyframe;
//                    prevSpaceKeyframe.requestResolveRelationship(this);
////                    prevKeyframe.checkResolve();
//                }
//            }
//        }
//        prevSpaceKeyframe.lengthenToNextKeyframe();
        resolveRequiredKeyframes();
        System.out.println("("+Name+") framestart:"+frameStart+"  length:"+length +"PrevKeyframeSpace:("+ prevSpaceKeyframe.getName()+")");
//        System.out.println("Prev space:"+prevSpace+" "+prevSpace.frameStart+ " "+prevSpace.getNewSpace().getPosition().x+" "+prevSpace.getNewSpace().getPosition().y);
        resolveNewSpace();
    }
    public void resolveRequiredKeyframes(){
        checkKeyframeResolve();
        super.resolveKeyframe();
    }
    public void resolveNewSpace(){
        //child fill implement if needed
    };//
    @Override
    public String getDebugInfo(){
        if(prevSpaceKeyframe !=null) {
            return super.getDebugInfo() + " prevKeyframe:(" + prevSpaceKeyframe.getName() + ") ";
        }
        return  super.getDebugInfo();
    }

    @Override
    public MotionSpaceKeyframe setName(String newName) {
        Name=newName;
        return this;
    }

}
