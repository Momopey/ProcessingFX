package com.javafx.editor;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class Region {
    // Reigon within a editor field with properties
    public int x;
    public int y;
    public int width;
    public int height;
    public int extraBound=0;

    boolean relativeToField=true;

    public Region(int dx,int dy, int dw,int dh){
        relativeToField=true;
        x=dx;
        y=dy;
        width=dw;
        height=dh;
        extraBound=0;
    }

    public Region(boolean dRel,int dx,int dy, int dw,int dh){
        relativeToField= dRel;
        x=dx;
        y=dy;
        width=dw;
        height=dh;
        extraBound=0;
    }
    public Region(int dx,int dy, int dw,int dh,int db){
        x=dx;
        y=dy;
        width=dw;
        height=dh;
        extraBound=db;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public boolean mouseEventInRange(EditorField field, MouseEvent event){
        double eventX=event.getX();
        double eventY=event.getY();
        if(relativeToField){
            return (eventX>field.getX()+x-extraBound)&&(eventX<field.getX()+x+width+extraBound)&&(eventY>field.getY()+y-extraBound)&&(eventY<field.getY()+y+height+extraBound);
        }else{
            return (eventX>x-extraBound)&&(eventX<x+width+extraBound)&&(eventY>y-extraBound)&&(eventY<y+height+extraBound);
        }

    }
    public void drawRangeGizmo(EditorField field,GraphicsContext editorGC){
        editorGC.setLineWidth(1);
        editorGC.setStroke(new javafx.scene.paint.Color(0.4,0.4,0.4,0.30));
        if(relativeToField){
            editorGC.strokeRect( field.getX()+x-extraBound,field.getY()+y-extraBound,width+extraBound*2,height+extraBound*2);
        }else{
            editorGC.strokeRect(x-extraBound,y-extraBound,width+extraBound*2,height+extraBound*2);
        }
    }
}
