package com.timeline.keyframe.motion;

import com.timeline.controller.TimelineController;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.space.KeyframeSpaceMotion;
import com.timeline.keyframe.space.KeyframeSpaceMotionTransform;
import com.util.SimpleMatrix;


//KeyframeMotionSpaceTransform: A transform that transforms relative to the space. Utilizes the pretransformation matrix.
public class KeyframeMotionSpaceTransform extends KeyframeMotionSpace {
    public KeyframeMotionSpaceTransform(int dFrameStart, int dLength) {
        super(dFrameStart, dLength);
    }
    public KeyframeMotionSpaceTransform(int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dFrameStart, dLength, dNewTransform);
    }
    public KeyframeMotionSpaceTransform(TimelineController dController, int dFrameStart, int dLength, SimpleMatrix dNewTransform) {
        super(dController, dFrameStart, dLength, dNewTransform);
    }



    @Override
    protected void createKeyframeSpace(){
        if (newSpace == null) {
            System.out.println(Name);
            newSpace = new KeyframeSpaceMotionTransform(frameStart, this);
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
            newSpaceMatrix=  newSpace.getPrevKeyframeSpace().getNewSpace().createApplied(spaceTransform);
        }else if(newSpaceMatrix!=null){
            newTransform = pretransformationMatrix.clone();
            newTransform.apply(newSpace.getPrevKeyframeSpace().getNewSpace().createInverse());
            newTransform.apply(newSpaceMatrix);
            newTransform.apply(pretransformationMatrix.createInverse());
        }
//        newSpace.resolveKeyframe();
        newSpace.resolveNewSpace();
    }
}
