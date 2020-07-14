package com.timeline.keyframe.motion;

import com.timeline.keyframeContainer.KeyframeContainer;
import com.timeline.keyframe.space.TransformMotionSpaceKeyframe;
import com.util.SimpleMatrix;


//TransformSpaceMotionKeyframe: A transform that transforms relative to the space. Utilizes the pretransformation matrix.
public class TransformSpaceMotionKeyframe extends SpaceMotionKeyframe {
    public TransformSpaceMotionKeyframe(int dFrameStart, int dLength) {
        super(dFrameStart, dLength);
    }
    public TransformSpaceMotionKeyframe(int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dFrameStart, dLength, dNewTransform);
    }
    public TransformSpaceMotionKeyframe(KeyframeContainer dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dController, dFrameStart, dLength, dNewTransform);
    }



    @Override
    protected void createKeyframeSpace(){
        if (newSpace == null) {
            System.out.println(Name);
            newSpace = new TransformMotionSpaceKeyframe(frameStart, this);
            newSpace.setName(Name + " Keyframe space transform");
            newSpace.requestResolveRelationship(this);
//                addRequiredResolveRelationship(newSpace);// newSpace depends on this keyframe
            addDependentResolveKeyframe(newSpace);
            getParentTimeline().spaceController.addKeyFrame(newSpace);
        }
//        if(newSpaceMatrix!=null)
//            spaceTransform=
//        }
        newSpace.resolveRequiredKeyframes();
        if(newTransform!=null) {
            spaceTransform = pretransformationMatrix.clone();
            spaceTransform.apply(newTransform);
            spaceTransform.apply(pretransformationMatrix.createInverse());
            newSpaceMatrix=  newSpace.getPrevSpaceKeyframe().getNewSpace().createApplied(spaceTransform);
        }else if(newSpaceMatrix!=null){
            newTransform = pretransformationMatrix.clone();
            newTransform.apply(newSpace.getPrevSpaceKeyframe().getNewSpace().createInverse());
            newTransform.apply(newSpaceMatrix);
            newTransform.apply(pretransformationMatrix.createInverse());
        }
//        newSpace.resolveKeyframe();
        newSpace.resolveNewSpace();
    }
}
