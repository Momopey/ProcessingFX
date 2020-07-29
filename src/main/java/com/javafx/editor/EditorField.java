package com.javafx.editor;

import com.processing.PAnim;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public abstract class EditorField implements EditorCanvasListener, Comparable<EditorField>{
    private int x;
    private int y;
    private int z; //Depth buffer, higher z values will render last

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
//        updateEditorReigons();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
//        updateEditorReigons();
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public ArrayList<Region> regions = new ArrayList<Region>();
    protected boolean followSroll= false;

    public boolean held;
    public Region heldReigon;
    public int heldTime;

    public EditorField(){
    }
    public void addToEditorCanvasHandler(){
        PAnim.processing.editorCanvasHandler.addEditorField(this);
    }
    public void removeFromEditorCanvasHandler(){ PAnim.processing.editorCanvasHandler.removeEditorField(this);}
    public void draw(EditorCanvasHandler editorCanvasHandler) {

    }
    public void updateEditor(){
        updateEditorPosition();
        updateEditorReigons();
    }
    public abstract void updateEditorPosition();

    public abstract void updateEditorReigons();

    @Override
    public void onMousePressed(MouseEvent event) {
        if(!held){
            for(Region region : regions){
                if(region.mouseEventInRange(this,event)){
                    held=true;
                    heldReigon=region;
                    heldTime=0;
                }
            }
        }
        if(held){
            onHoldDrag(event);
            heldTime++;
        }
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if(held){
            onHoldDrag(event);
            heldTime++;
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        if(held){
            onHoldDrag(event);
        }
        held=false;
    }

    @Override
    public void onKeyPressed(KeyEvent event) {

    }

    public void onHoldDrag(MouseEvent event){

    }


    @Override
    public int compareTo(EditorField o) {
        if(z>o.z){
            return 1;
        }else if (z<o.z){
            return -1;
        }else{
            return 0;
        }
    }
}
