package com.timeline.keyframeController;

import com.debug.Debugable;
import com.mode.Mode;
import com.timeline.keyframe.Keyframe;
import com.timeline.Timeline;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

//Timeline controller: A container for keyframes controlling the animation of the symbol associated with the timeline, and its children.
// Has only a few important functions:
//1: Load a frame (given a frame number inside of the parent timeline) -> Applies keyframes to symbols
//2: Check resolvement -> Insures that the keyframes to be loaded are resolved
//3: Manage the addition/Removal/general modification of keyframes (This is more general and tends to be done by more specific controller classes)
//4: Give some useful functions that child keyframes can use (Same idea of being very general).
public class KeyframeController implements Debugable {
    protected String Name;

    protected Timeline parentTimeline;
    protected Mode mode;
    protected ArrayList<Keyframe> keyframes;

    public Timeline getParentTimeline() {
        return parentTimeline;
    }

    public void setParentTimeline(Timeline parentTimeline) {
        this.parentTimeline = parentTimeline;
    }

    public Mode getMode() {
        return mode;
    }

    public ArrayList<Keyframe> getKeyframes() {
        return keyframes;
    }
    public ArrayList<Keyframe> getKeyframesAt(int selectFramestart){
        ArrayList<Keyframe> selectKeyframes= new ArrayList<>();
        for(Keyframe k : keyframes){
            if(k.getFrameStart()==selectFramestart){
                selectKeyframes.add(k);
            }
        }
        return selectKeyframes;
    }
    public ArrayList<Keyframe> getKeyframesContaining(int selectFramestart){
        ArrayList<Keyframe> selectKeyframes= new ArrayList<>();
        for(Keyframe k : keyframes){
            if(k.containsFrame(selectFramestart)){
                selectKeyframes.add(k);
            }
        }
        return selectKeyframes;
    }
    public KeyframeController(Timeline dParent, Mode dMode){
        parentTimeline= dParent; mode=dMode;
        keyframes= new ArrayList<>();
    }
    public void checkResolvement(int dFrameNumber){
        for(int keyframenum=0;keyframenum<keyframes.size();keyframenum++) {// first resolve keyframes
            Keyframe thiskeyframe = keyframes.get(keyframenum);
//            int frameNum = thiskeyframe.getFrameNumberIn(dFrameNumber);
            if (thiskeyframe.needsToBeResolvedAt(dFrameNumber)) {
                    thiskeyframe.resolveKeyframe();
            }
        }
    }
    public void LoadKeyframe(int dFrameNumber){//framenumber relatve to start os scene
//        System.out.println("Loading frame, modifying details");
        for(int keyframenum=0;keyframenum<keyframes.size();keyframenum++){// then load
            Keyframe thiskeyframe= keyframes.get(keyframenum);
            int frameNum=thiskeyframe.getFrameNumberIn(dFrameNumber);
            if(frameNum>=0) {
                if (frameNum < thiskeyframe.getLength()) {
                    thiskeyframe.inFrameModifyDetails(frameNum);
                }
                if (frameNum > thiskeyframe.getLength()) {
                    thiskeyframe.afterFrameModifyDetails(frameNum);
                }
            }

        }
    }
    public void addKeyFrame(Keyframe newKeyframe){
        newKeyframe.setParentController(this);
        for(int i =keyframes.size()-1;i>=0;i--){
            if(keyframes.get(i).getFrameStart()<=newKeyframe.getFrameStart()){
                keyframes.add(i+1,newKeyframe);
                return;
            }
        }
        //if before all, add to start
        keyframes.add(newKeyframe);
    }
    public boolean beforeController(KeyframeController controller){
        if(parentTimeline.controllers.contains(controller)) {
            return parentTimeline.controllers.indexOf(controller) > parentTimeline.controllers.indexOf(this);
        }
        return true;
    }
    public ArrayList<KeyframeController> getControllersBefore(){
        ArrayList<KeyframeController> controllersBefore= new ArrayList<>();
        for(int i=0;i<parentTimeline.controllers.size();i++){
            if(parentTimeline.controllers.get(i)==this){
                return controllersBefore;
            }
            controllersBefore.add(parentTimeline.controllers.get(i));
        }
        return controllersBefore;
    }

    public void fixController(){
        for(int i=0; i<keyframes.size();i++){
            keyframes.get(i).resolveKeyframe();
        }
    }

    public int indexInTimeline(){
        return parentTimeline.controllers.indexOf(this);
    }
    public void drawKeyframesInTimeline(GraphicsContext editorGC){
        for(Keyframe K:keyframes) {
            K.drawKeyframeInTimeline(editorGC);
        }
    }


    //Debugable
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getDebugInfo() {
        return "| ("+Name+") debug info- TODO |";
    }

    @Override
    public KeyframeController setName(String newName) {//A self returner builder pattern
        Name=newName;
        return this;
    }
}
