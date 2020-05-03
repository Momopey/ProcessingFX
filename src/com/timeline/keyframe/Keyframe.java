package com.timeline.keyframe;

import com.debug.Debugable;
import com.javafx.Controller;
import com.mode.Mode;
import com.symbol.Symbol;
import com.timeline.Timeline;
import com.timeline.controller.TimelineController;
import com.timeline.keyframe.statechange.StateChangeKeyframe;
import com.util.SimpleMatrix;
import javafx.scene.canvas.GraphicsContext;

import java.security.Key;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

//Keyframe: A class for keyframes within timelinecontrollers. Controlls certain parameters in the parent symbol.
public class Keyframe implements Debugable{
    protected String Name;

    protected int frameStart;
    protected int length;

    public int getFrameStart() {
        return frameStart;
    }

    public void setFrameStart(int frameStart) {
        this.frameStart = frameStart;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    protected Mode mode;
    protected TimelineController parentController;
    protected boolean resolved=false;//Keyframe will only animate if resolved
    protected LinkedHashSet<Keyframe> requiredResolve;// The keyframes that are required to be resolved for this keyframe to be resolved;
    //If this keyframe is asked to resolve itself (during a frameload), it must first resolve all of these keyframes before resolving itself (if they aren't resolved).
    protected LinkedHashSet<Keyframe> dependentResolve;// The keyframes that require this keyframe to be resolved, to be resolved;
    //If this keyframe becomes unresolved (before the render/after changes), all of the keyframes in dependent Resolve must recheck their resolution

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public TimelineController getParentController() {
        return parentController;
    }

    public Timeline getParentTimeline(){
        return parentController.getParentTimeline();
    }

    public Symbol getParentSymbol(){
        return parentController.getParentTimeline().parentSymbol;
    }

    public Keyframe(Mode dMode, int dFrameStart, int dLength){
        frameStart=dFrameStart;
        length=dLength;
        mode=dMode;
        requiredResolve=new LinkedHashSet<>();
        dependentResolve=new LinkedHashSet<>();
//        if(mode != parentController.getMode()){
//            System.out.println("Wrong mode for keymode");
//        }
    }
    public Keyframe(Mode dMode,TimelineController dParentController, int dFrameStart, int dLength){
        frameStart=dFrameStart;
        length=dLength;
        mode=dMode;
        parentController=dParentController;
        if(mode != parentController.getMode()){
            System.out.println("Wrong mode for keymode");
        }
    }

    public void setParentController(TimelineController parentController) {
        this.parentController = parentController;
    }

    public int getFrameNumberIn(int frameInController){
        return frameInController-frameStart;
    }
    public int getFrameNumberOut(int frameInKeyframe){
        return frameInKeyframe+frameStart;
    }

    public boolean containsFrame(int frame){
        return (getFrameStart()<=frame)&&(getFrameStart()+getLength()>frame);
    }

    public Keyframe getFollowingKeyframe(int offset){
        int ind= getParentController().getKeyframes().indexOf(this);
        ind+=offset;
        if(ind<0||ind>=getParentController().getKeyframes().size()){
            return null;
        }else{
            return getParentController().getKeyframes().get(ind);
        }
    }
    public Keyframe nextKeyframe(){
        return getFollowingKeyframe(1);
    }
    public Keyframe getPrevKeyframe(){
        return getFollowingKeyframe(-1);
    }

    public Keyframe lengthenToNextKeyframe(){//Thisreturner
        Keyframe next= nextKeyframe();
        if(next != null){
            if(next.frameStart>=frameStart){//Now allowing 0 length keyframes
                length=(next.frameStart-frameStart);
            }
        }else{
            length= parentController.getParentTimeline().length-frameStart;
//            System.out.println("New length:"+length+"   Parent length:"+parentController.getParentTimeline().length+" ");
        }
        return this;
    }
    public int followingKeyframeStart(int offset){//Smart keyframe start chops to controller length or 0
        int ind= getParentController().getKeyframes().indexOf(this);
        ind+=offset;
        if(ind<0){
            return 0;
        }else if(ind>=getParentController().getKeyframes().size()){
            return getParentTimeline().length;
        }else{
            return getParentController().getKeyframes().get(ind).getFrameStart();
        }
    }
    public void fixKeyframeIndex(){
        int nextframestart=followingKeyframeStart(1);
        int prevframestart=followingKeyframeStart(-1);
        System.out.println("Following keyframe start:"+nextframestart+" previous start:"+prevframestart+"  framestart:"+frameStart);
        if(nextframestart<getFrameStart()||prevframestart>getFrameStart()) {
            if (!(nextframestart < getFrameStart() && prevframestart > getFrameStart())) {
                parentController.getKeyframes().remove(this);
                for (int i = parentController.getKeyframes().size() - 1; i >= 0; i--) {
                    if (parentController.getKeyframes().get(i).getFrameStart() <= frameStart) {
                        //ASSUMING ALL OTHER keyframes are sorted which is a dangerous assumption but whatever for now
                        parentController.getKeyframes().add(i+1,this);
                        System.out.println("("+Name+"Changed the index of keyframe to "+i);
                        return;
                    }
                }
            }else{
                System.out.println("Fixing keyframe index cant be done because the other keyframes arent sorted already");
            }
        }
    }
    public void inFrameModifyDetails(int dFrameNum){};//framenum relatvie to start of keyframe
    public void afterFrameModifyDetails(int dFrameNum){};//framenum relatvie to start of keyframe, run code after the keyframe : USE SPARINGLY



    /// Keyframe resolvement
    protected void addDependentResolveRelationship(Keyframe dependentKeyframe){
        dependentResolve.add(dependentKeyframe);
        dependentKeyframe.requiredResolve.add(this);
    }
    protected void removeDependentResolveRelationship(Keyframe prevDepKeyframe){
        dependentResolve.remove(prevDepKeyframe);
        prevDepKeyframe.requiredResolve.remove(this);
    }
    protected void addRequiredResolveRelationship(Keyframe requiredKeyframe){
        requiredResolve.add(requiredKeyframe);
        requiredKeyframe.dependentResolve.add(this);
    }
    protected void removeRequiredResolveRelationship(Keyframe prevReqKeyframe){
        requiredResolve.remove(prevReqKeyframe);
        prevReqKeyframe.dependentResolve.remove(this);
    }
    protected void addDependentResolveKeyframe(Keyframe dependentKeyframe){
        dependentResolve.add(dependentKeyframe);
    }
    protected void removeDependentResolveKeyframe(Keyframe prevDep){
        dependentResolve.remove(prevDep);
    }
    protected void addRequiredResolveKeyframe(Keyframe requiredKeyframe){
        requiredResolve.add(requiredKeyframe);
    }
    protected void removeRequiredResolveKeyframe(Keyframe prevRequiredKeyframe){
        requiredResolve.remove(prevRequiredKeyframe);
    }
    public void requestResolveRelationship(Keyframe requester){
    }
    public void removeResolveRelationship(Keyframe requester){
    }

    public boolean isResolved() {
        return resolved;
    }
    public boolean needsToBeResolvedAt(int frameNumberOut){
        if (containsFrame(frameNumberOut)){
            if (!isResolved()) {
                return true;
            }
        }
        return false;
    }
    public void resolveKeyframe(){
        resolved=true;
        String namesConcat="";
        for(Keyframe req:requiredResolve){
            namesConcat+=req.Name+",";
        }
        System.out.println("v: Resolving keyframe ("+Name+") needs to resolve "+requiredResolve.size() + " keyframes:("+namesConcat+")    framestart:"+frameStart);
        for (Keyframe K : requiredResolve) {
//                System.out.println("K:"+K.isResolved());
            if (!K.isResolved()) {
                K.resolveKeyframe();
            }
        }
        System.out.println("^: Finished resolving ("+Name+")'s required keyframes");
        // Implementations should do other resolvement stuff here (and call super before)
    }
    public void deResolve(){
        System.out.println("It just got deresolved");
        resolved=false;
//        checkResolve();
        checkDependentResolves();
        Controller.p.debugInfo();
    }
    public void checkResolve(){
//      Check if everything is resolved( everything about the requiredResolve is changed or not)
//      Even if a requiredResolve has changed, it may not change its resolvement if the important variables are unchanged
//      Implementations should run some code before calling super.
        checkKeyframeResolve();
        if((!isResolved())) {
            checkDependentResolves();
        }
    }
    public void checkKeyframeResolve(){
        resolved=false;//Implementations need to be more intelegent here
    }
    public void checkDependentResolves(){
        String namesConcat="";
        for(Keyframe dep:dependentResolve){
            String Name=dep.Name;
            namesConcat+=Name+",";
        }
        System.out.println("v: deresolving depenendent keuframe:  ("+Name+") needs to deresolve "+dependentResolve.size()+" keyframes ("+namesConcat+")  ");
        for(Keyframe K:dependentResolve){
            if(K.isResolved()) {
                K.checkResolve();
            }
        }
        System.out.println("^: done deresolving :("+Name+")");
    }

    //StateChanges
    public void stateChange(StateChangeKeyframe stateChange){
        stateChange.change(this);
        deResolve();
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public String getDebugInfo() {
        return "| ("+Name+") debug info- resolved:"+resolved+"  framestart:"+frameStart+" length:"+length+"  index in controller:"+parentController.getKeyframes().indexOf(this);
    }

    @Override
    public Keyframe setName(String newName) {//A self returner builder pattern
        Name=newName;
        return this;
    }

    public void drawKeyframeInTimeline(GraphicsContext editorGC) {//Temporary
        int x=frameStart;
        int y=getParentController().indexInTimeline();
        int w=length;
        editorGC.setFill(new javafx.scene.paint.Color(0.730,0.7,0.7,0.94));
        editorGC.setStroke(new javafx.scene.paint.Color(0.4,0.4,0.4,0.80));
        editorGC.fillRect(12*(6+x)+1,15+25*(y)+1,12*w,25-2);
        editorGC.strokeRect(12*(6+x)+1,15+25*(y)+1,12*w,25-2);
        //Resolved icon
        editorGC.strokeOval(12*(6+x)+4,15+25*(y)+4,6,6);
        if(!isResolved()) {
            editorGC.strokeLine(12 * (6 + x) + 4, 15 + 25 * (y) + 4, 12 * (6 + x) + 4 + 6, 15 + 25 * (y) + 4 + 6);
            editorGC.strokeLine(12 * (6 + x) + 4 + 6, 15 + 25 * (y) + 4, 12 * (6 + x) + 4, 15 + 25 * (y) + 4 + 6);
        }
        //keyframe icon
        editorGC.strokeRect(12*(6+x)+3,15+25*(y)+10+3,8,8);
        //Length icons
        if(w>=3) {
            editorGC.strokeRect(12 * (6 + x) + 10 + 4 + 18 + 3, 15 + 25 * (y) + 4, 12 * w - 10 - 4 - 18 - 3 - 3, 8);//for when special icon is shown
        }else if(w>=1){
            editorGC.strokeRect(12*(6+x)+10+4,15+25*(y)+4,12*w-10-4-3,8);// otherwise
        }
        //Special icon
        if(w>=3) {
            editorGC.strokeRect(12 * (6 + x) + 10 + 4, 15 + 25 * (y) + 3, 18, 18);
        }
    }
}
