package com.javafx.editor;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface EditorCanvasListener{
    void onMousePressed(MouseEvent event);
    void onMouseDragged(MouseEvent event);
    void onMouseReleased(MouseEvent event);
    void onKeyPressed(KeyEvent event);
}
