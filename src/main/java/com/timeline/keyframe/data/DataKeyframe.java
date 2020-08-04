package com.timeline.keyframe.data;

import com.mode.Mode;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.data.space.SpaceKeyframe;
import com.timeline.keyframe.motion.SpaceMotionKeyframe;
import com.timeline.keyframeController.KeyframeController;

import java.util.ArrayList;

//SpaceKeyframe: Keyframe containing a space matrix
public abstract class DataKeyframe extends Keyframe {
    protected boolean historyDependent;
    protected DataKeyframe nextDataKeyframe;
    protected DataKeyframe prevDataKeyframe;
    protected ArrayList<DataModifierKeyframe> dataModifiers= new ArrayList<>();

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
            System.out.println("Space keyframe("+Name+") adding a depentent space keyframe("+requester.getName()+")");
        }
        if(requester instanceof DataModifierKeyframe){
            DataModifierKeyframe dataModfier= (DataModifierKeyframe) requester;
            System.out.println("DATAMODIFIER ATTEMPT ATTACHMENT - DATAMODIFIER ATTEMPT ATTACHMENTDATAMODIFIER ATTEMPT ATTACHMENT");
            if(dataModfier.getDataMode().getDataClass().isInstance(this)){
                addDependentResolveKeyframe(requester);
                addRequiredResolveRelationship(requester);
                addDataModifier(dataModfier);
            }
        }
    }
    public void addDataModifier(DataModifierKeyframe dataModifier){
        dataModifiers.add(dataModifier);
        if(dataModifier.causesHistoryDependance()){
            historyDependent=true;
        }
    }

    @Override
    public void removeResolveRelationship(Keyframe requester){
        if(getClass().isInstance(requester)){
            removeDependentResolveRelationship(requester);
            System.out.println("Space keyframe("+Name+") removing a depentent space keyframe("+requester.getName()+")");
        }
        if(requester instanceof DataModifierKeyframe){
            DataModifierKeyframe dataModfier= (DataModifierKeyframe) requester;
            if(dataModfier.getDataMode().getDataClass().isInstance(this)){
                removeRequiredResolveKeyframe(requester);
                removeDependentResolveKeyframe(requester);
                removeDataModifier(dataModfier);
            }
        }

    }
    public void removeDataModifier(DataModifierKeyframe dataModifier){
        dataModifiers.remove(dataModifier);
        checkHistoryDependance();
    }
    public void checkHistoryDependance(){
        boolean histDep=false;
        for(DataModifierKeyframe d: dataModifiers){
            if(d.causesHistoryDependance()){
                histDep=true;
            }
        }
        historyDependent=histDep;
    }
    @Override
    public void checkKeyframeResolve() {
        super.checkKeyframeResolve();
        loadPrevKeyframe();
        loadNextKeyframe();
        lengthenToNextKeyframe();//Maybe a bad idea but i dont think so
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
                }
            } else {
                System.out.println("SpaceKeyframe next keyframe not instance of SpaceKeyframe");
            }
        }else{
            nextDataKeyframe =null;
        }
    }
    protected void loadPrevKeyframe(){
        Keyframe prevKeyframe = getPrevKeyframe();
        if(prevKeyframe==null){
            System.out.println("Keyframe Space Transform previous keyframe does not exist");
        }else{
            if(prevKeyframe instanceof SpaceKeyframe) {
                if(prevDataKeyframe==null){
                    prevDataKeyframe = (SpaceKeyframe)prevKeyframe;
                    if(historyDependent){
                        prevDataKeyframe.requestResolveRelationship(this);
                    }
                }else if (prevKeyframe != prevDataKeyframe) {
                    prevDataKeyframe.removeResolveRelationship(this);
                    prevDataKeyframe = (SpaceKeyframe)prevKeyframe;
                    if(historyDependent){
                        prevDataKeyframe.requestResolveRelationship(this);
                    }
                }
            }
        }
        if(prevDataKeyframe!=null){
            prevDataKeyframe.lengthenToNextKeyframe();
        }

    }
    @Override
    public DataKeyframe setName(String newName) {
        Name=newName;
        return this;
    }
}
