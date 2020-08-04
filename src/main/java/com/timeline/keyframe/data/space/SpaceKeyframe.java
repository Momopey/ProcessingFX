package com.timeline.keyframe.data.space;

import com.mode.Mode;
import com.processing.PAnim;
import com.timeline.keyframe.data.DataKeyframe;
import com.timeline.keyframe.data.DataModifierKeyframe;
import com.timeline.keyframeController.KeyframeController;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.motion.SpaceMotionKeyframe;
import com.util.SimpleMatrix;

//SpaceKeyframe: Keyframe containing a space matrix
public class SpaceKeyframe extends DataKeyframe {
    protected SimpleMatrix newSpace;

    public SimpleMatrix getNewSpace() {
        return newSpace;
    }

    public Keyframe setNewSpace(SimpleMatrix newSpace) {
        this.newSpace = newSpace;
        return this;
    }

    public SpaceKeyframe getPrevSpaceKeyframe() {
        return (SpaceKeyframe) prevDataKeyframe;
    }
    public SpaceKeyframe(int dFrameStart) {
        super(PAnim.MODETimelineSpace, dFrameStart);
    }
    public SpaceKeyframe(int dFrameStart, SimpleMatrix dSpace) {
        super(PAnim.MODETimelineSpace, dFrameStart);
        newSpace=dSpace;
//        historyDependent=true;
    }
    public SpaceKeyframe(Mode dMode, KeyframeController dParentController, int dFrameStart, SimpleMatrix dSpace) {
        super(dMode, dParentController, dFrameStart);
        newSpace=dSpace;
//        historyDependent=true;
    }

    public void applyTransform(SimpleMatrix trans){
        newSpace= newSpace.createApplied(trans);
//                apply(trans);
    }
    public void applyToTransform(SimpleMatrix trans){
        newSpace= trans.createApplied(newSpace);
    }
//    @Override
    public void combineWithKeyframe(SpaceKeyframe dPrevSpace){
        if(prevDataKeyframe ==null) {
            dPrevSpace.lengthenToNextKeyframe();
            prevDataKeyframe = dPrevSpace;
            prevDataKeyframe.requestResolveRelationship(this);
        }
    };


    @Override
    public void inFrameModifyDetails(int dFrameNum) {
        getParentSymbol().setSpaceMatrix(newSpace);
//
    }
    @Override
    public void checkKeyframeResolve(){
        resolved=false;

        boolean allresolved=true;
        for(DataModifierKeyframe d: dataModifiers) {
            if(!d.checkResolveKeyframe(this)){
                allresolved=false;
            }
        }
        resolved= allresolved;
//        if(frameStart!=transformKeyframe.getFrameStart()){
//            frameStart=transformKeyframe.getFrameStart();
//            fixKeyframeIndex();
//        }
        super.checkKeyframeResolve();
    }
    @Override
    public void resolveKeyframe(){

        resolveRequiredKeyframes();
//        System.out.println("("+Name+") framestart:"+frameStart+"  length:"+length +"PrevKeyframeSpace:("+ prevDataKeyframe.getName()+")");
//        System.out.println("Prev space:"+prevSpace+" "+prevSpace.frameStart+ " "+prevSpace.getNewSpace().getPosition().x+" "+prevSpace.getNewSpace().getPosition().y);
        resolveModifiers();

    }
    public void resolveRequiredKeyframes(){
        checkKeyframeResolve();
        super.resolveKeyframe();
    }
    public void resolveModifiers(){
        //child fill implement if needed
        for(DataModifierKeyframe d: dataModifiers){
            System.out.println("UEuHUEFEIUIUECNU- THERE IS A MODIFIER THERE IS ONE");
            d.modifyKeyframe(this);
        }
//        newSpace=transformKeyframe.getNewSpaceMatrix().clone();
    };//
//    @Override
//    public void requestResolveRelationship(Keyframe requester){
//        if(requester instanceof SpaceKeyframe){
//            addDependentResolveRelationship(requester);
//            nextDataKeyframe = (SpaceKeyframe) requester;
//            System.out.println("Space keyframe("+Name+") adding a depentent space keyframe("+requester.getName()+")");
//        }
//    }
//    @Override
//    public void removeResolveRelationship(Keyframe requester){
//        super.removeResolveRelationship(requester);
//    }
    @Override
    public String getDebugInfo(){
        if(nextDataKeyframe !=null) {
            return super.getDebugInfo() + " nextKeyframe:(" + nextDataKeyframe.getName() + ") ";
        }
        return  super.getDebugInfo();
    }
}