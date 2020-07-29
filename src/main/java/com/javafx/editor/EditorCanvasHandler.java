package com.javafx.editor;

import com.javafx.Controller;
import com.symbol.ParentSymbol;
import com.timeline.keyframe.Keyframe;
import com.timeline.keyframeContainer.KeyframeContainer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sun.jvm.hotspot.ui.Editor;

import java.util.ArrayList;
import java.util.Collections;

public class EditorCanvasHandler {
    private ParentSymbol editorActiveSymbol;

    public void setActiveSymbol(ParentSymbol mainScene) {
        if(editorActiveSymbol!=null){
            for(KeyframeContainer ct: editorActiveSymbol.getTimeline().controllers){
                for(Keyframe kf: ct.getKeyframes()){
                    kf.removeFromEditorCanvasHandler();
                }
            }
        }
        editorActiveSymbol=mainScene;
        for(KeyframeContainer ct: editorActiveSymbol.getTimeline().controllers){
            for(Keyframe kf: ct.getKeyframes()){
                kf.addToEditorCanvasHandler();
            }
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }

    Canvas canvas;

    public GraphicsContext getGC() {
        return gc;
    }

    GraphicsContext gc;

    ArrayList<EditorCanvasListener> listeners = new ArrayList<EditorCanvasListener>();
    ArrayList<EditorField> fields= new ArrayList<EditorField>(){
        public boolean add(EditorField mt) {
            int index = Collections.binarySearch(this, mt);
            if (index < 0) index = ~index;
            super.add(index, mt);
            return true;
        }
    };


    double canvasWidth;
    double canvasHeight;

    //DebugTrash
    boolean garbageVerbose= false;

    public void init(){
        canvas= Controller.controller.editorCanvas;
        gc= Controller.controller.editorGC;
        canvasWidth = gc.getCanvas().getWidth();
        canvasHeight = gc.getCanvas().getHeight();
    }

    public ParentSymbol getEditorActiveSymbol() {
        return editorActiveSymbol;
    }

    public void draw(){
        editorActiveSymbol.drawSymbolTimeline(this);
        for(EditorField field :fields){
            field.draw(this);
        }
    }

    public void addEditorCanvasListener(EditorCanvasListener listener){
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }
    public void addEditorField(EditorField field){
        if(!fields.contains(field)){
            fields.add(field);
        }
        if(!listeners.contains(field)) {
            listeners.add(field);
        }
    }
    public void removeEditorField(EditorField editorField) {
        fields.remove(editorField);
        listeners.remove(editorField);
    }
    public void handleMousePressed(MouseEvent event){
        if(garbageVerbose) {
            System.out.println("MousePressed at x:" + event.getX() + " y:" + event.getY());
        }
        for(EditorCanvasListener listener: listeners){
            listener.onMousePressed(event);
        }
    }
    public void handleMouseDragged(MouseEvent event){
        if(garbageVerbose){
            System.out.println("MouseDragged to x:"+event.getX()+" y:"+event.getY());
        }
        for(EditorCanvasListener listener: listeners){
            listener.onMouseDragged(event);
        }

    }
    public void handleMouseReleased(MouseEvent event){
        if(garbageVerbose){
            System.out.println("MouseReleased at x:"+event.getX()+" y:"+event.getY());
        }
        for(EditorCanvasListener listener: listeners){
            listener.onMouseReleased(event);
        }

    }

    public void handleKeyPressed(KeyEvent event) {
        if(garbageVerbose){
            System.out.println("KeyPressed :"+event.getText());
        }
        for(EditorCanvasListener listener: listeners){
            listener.onKeyPressed(event);
        }
    }


}
