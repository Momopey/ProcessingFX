package com.javafx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import processing.javafx.PSurfaceFX;

public class App extends Application {

    public static PSurfaceFX surface;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/ProcessingFX.fxml"));
        Parent root = loader.load();
        Controller.stage = primaryStage;
        Scene scene = new Scene(root, 1280, 720);

        primaryStage.setTitle("PAnim Editor (WIP)");
        primaryStage.setScene(scene);
        primaryStage.show();

        surface.stage = primaryStage;
        Controller.stage = primaryStage;

        Controller controller = loader.getController();
//        PAnim.processing.canvasHandler.canvas= controller.editorCanvas;
//        PAnim.processing.canvasHandler.gc= controller.editorGC;

        //Allow editorCanvas to have keyPressed listeners and stuff
        controller.editorCanvas.setFocusTraversable(true);
        controller.editorCanvas.addEventFilter(MouseEvent.ANY, (e) -> controller.editorCanvas.requestFocus());

        controller.editorCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        Controller.p.editorCanvasHandler.handleMousePressed(event);
                    }
                });
        controller.editorCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        Controller.p.editorCanvasHandler.handleMouseDragged(event);
                    }
                });

        controller.editorCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>(){

                    @Override
                    public void handle(MouseEvent event) {
                        Controller.p.editorCanvasHandler.handleMouseReleased(event);
                    }
                });
        controller.editorCanvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                Controller.p.editorCanvasHandler.handleKeyPressed(event);
//                if (event.getCode() == KeyCode.ENTER) {
//                    System.out.println("Enter Pressed");
//                }
            }
        });

    }
}
