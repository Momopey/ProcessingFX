package com.timeline.keyframe.motion;

import com.timeline.keyframe.space.MotionSpaceKeyframe;
import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframeContainer.MotionKeyframeContainer;
import com.timeline.keyframe.Keyframe;
import com.util.SimpleMatrix;

import java.util.ArrayList;

// Keyframe motion space: an abstract class regarding keyframes that have a transform that turns into a space transform, resulting in a permanent transormation. Permanent too if a tween is used before it.
public abstract class SpaceMotionKeyframe extends MotionKeyframe {
    SimpleMatrix pretransformationMatrix;
    SimpleMatrix spaceTransform;
    SimpleMatrix newSpaceMatrix;
    MotionSpaceKeyframe newSpace;
    public SpaceMotionKeyframe(int dFrameStart, int dLength) {
        super(dFrameStart, dLength);
    }
    public SpaceMotionKeyframe(int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dFrameStart, dLength, dNewTransform);
    }
    public SpaceMotionKeyframe(KeyframeContainer dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dController, dFrameStart, dLength, dNewTransform);
    }

    @Override
    public Keyframe setNewTransform(SimpleMatrix newTransform) {
        this.newTransform = newTransform;
        this.newSpaceMatrix=null;
        return this;
    }

    public Keyframe setNewSpaceMatrix(SimpleMatrix newSpaceMatrix) {
        this.newSpaceMatrix = newSpaceMatrix;
        this.newTransform=null;
        return this;
    }
    public SimpleMatrix getNewSpaceMatrix() {
        return newSpaceMatrix;
    }

    @Override
    public void inFrameModifyDetails(int dFrameNum) {
//        newSpace.inFrameModifyDetails(0);
        pretransformationMatrix =getParentSymbol().getTransformMatrix().clone();
        //set pretransformation
    }

    public SimpleMatrix getSpaceTransform() {
        return spaceTransform;
    }
    public void findTransformRelationships(){
        ArrayList<KeyframeContainer> prevControllers= getParentController().getControllersBefore();
        for(KeyframeContainer c :prevControllers){
            if(c instanceof MotionKeyframeContainer){
                ArrayList<Keyframe> keyframesAt= ((MotionKeyframeContainer)c).getKeyframesContaining(frameStart);
                for(Keyframe k:keyframesAt){
                    if(k instanceof MotionKeyframe){
                        MotionKeyframe motionkey= (MotionKeyframe)k;
                        motionkey.requestResolveRelationship(this);
                    }
                }
            }
        }
    }
    public void removeTransformRelations(){
        for(Keyframe rel: requiredResolve){
            if(parentController.beforeController(rel.getParentController())||!rel.containsFrame(frameStart)){
                if(rel instanceof MotionKeyframe) {
                    rel.removeResolveRelationship(this);
                }
            }
        }
    }
    @Override
    public void resolveKeyframe(){
        //The transformation matrix is the pretransform times matrix times pretransform inverse
        resolved=true;
//        if(pretransformationMatrix==null){
            findTransformRelationships();
//        }else{
            removeTransformRelations();
//        }
        System.out.println("Recalculating spaceTransform ("+Name+") finding"+ requiredResolve.size()+" transform relationships");
        super.resolveKeyframe();
        ArrayList<KeyframeContainer> prevControllers= getParentController().getControllersBefore();
        pretransformationMatrix=new SimpleMatrix();
        for(KeyframeContainer c :prevControllers){
            if(c instanceof MotionKeyframeContainer){
                ArrayList<Keyframe> keyframesAt= ((MotionKeyframeContainer)c).getKeyframesContaining(frameStart);
                for(Keyframe k:keyframesAt){
                    if(k instanceof MotionKeyframe){
                        MotionKeyframe motionkey= (MotionKeyframe)k;
                        motionkey.resolveKeyframe();
                        System.out.println("( "+motionkey.getName()+" ) is a related transform with length "+motionkey.getLength());
                        pretransformationMatrix.apply(motionkey.getTransformAt(motionkey.getFrameNumberIn(getFrameNumberOut(0))));
                        motionkey.requestResolveRelationship(this);
                    }
                }
            }
        }
        createKeyframeSpace();
        System.out.println("spacecontroller number of keyframes:"+getParentTimeline().spaceController.getKeyframes().size());

//        super.resolveKeyframe();

    }
    protected abstract void createKeyframeSpace();//Implement by children
    @Override
    public void requestResolveRelationship(Keyframe requester) {
        if(requester instanceof TweenTransformMotionKeyframe){
            addDependentResolveRelationship(requester);
            System.out.println("A tween requested for space transform from ("+ requester.getName()+") to ("+Name+")");
        }
        super.requestResolveRelationship(requester);
    }
    public boolean needsToBeResolvedAt(int frameNumberOut){
        if (containsFrame(frameNumberOut)){
            if (!isResolved()) {
                return true;
            }
        }
        if(getFrameNumberIn(frameNumberOut)>0){
            if(newSpace==null){
                return true;
            }
        }
        return false;
    }
    @Override
    public String getDebugInfo(){
        if(newSpace==null){
            return super.getDebugInfo();
        }
        return super.getDebugInfo()+newSpace.getDebugInfo();
    }
}
