package com.timeline.keyframeController;

import com.mode.DataMode;
import com.processing.PAnim;
import com.timeline.Timeline;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframe.data.DataKeyframe;
import com.timeline.keyframe.data.space.SpaceKeyframe;
import com.timeline.keyframeController.data.DataKeyframeController;
import com.util.SimpleMatrix;

import java.util.ArrayList;

//TimelineControllerSpace: A timeline controller limited to space keyframes. Every symbol is preequipped with one of these.
// It has some special functionality regarding the addition of new keyframes, insuring every space keyframe is filling, and knows its neigbors.
public class SpaceKeyframeController extends DataKeyframeController<SpaceKeyframe> {
    public SpaceKeyframeController(Timeline dParent) throws ClassCastException {
        super(dParent, PAnim.MODETimelineSpace);
    }
//    protected Class<T> tClass;
//    public SpaceKeyframeController(Timeline dParent, DataMode dDataMode) throws ClassCastException{
//        super(dParent, dDataMode);
//        try{
//            tClass= (Class<T>) dDataMode.getDataClass();
//        }catch(ClassCastException e){
//            throw e;
//        }
//    }
//
//    @Override
//    public void addKeyFrame(Keyframe newKeyframe){
//        newKeyframe.setParentController(this);
//
//        ArrayList<Keyframe> KeyframeOn= getKeyframesContaining(newKeyframe.getFrameStart());
//        if(KeyframeOn.size()==0){
//            System.out.println("Timeline cotroller space is messed up, not everything is full, or something else");
//            return;
//        }
//        if(!(tClass.isInstance(newKeyframe))){
//            System.out.println("Timeline cotroller space is messed up, keyframe not instace of keyframeSpace");
//            return;
//        }
//        T newkeyframe= (T) newKeyframe;
//        if(!(tClass.isInstance(KeyframeOn.get(0)))){
//            System.out.println("Timeline cotroller space is messed up, keyframeon not instace of keyframeSpace");
//            return;
//        }
//        T keyframe= (T) KeyframeOn.get(KeyframeOn.size()-1);
////        newkeyframe.applyToTransform(keyframe.getNewSpace());
//        newkeyframe.combineWithKeyframe(keyframe);
//////        newKeyframe.addRequiredResolveKeyframe(keyframe); now  part of the combine with keyframe
//        //I just removed the previous frame removal deliniation: check if this becomes a problem later
//        super.addKeyFrame(newKeyframe);
//        keyframe.resolveKeyframe();
//    }

    public void SetDefaultSpace(SimpleMatrix defaultSpace){
        SpaceKeyframe firstframe= new SpaceKeyframe(0,defaultSpace);
        firstframe.setName("Default Space");
        firstframe.setParentController(this);
//        firstframe.resolveKeyframe();
        keyframes.add(firstframe);
    }

}
