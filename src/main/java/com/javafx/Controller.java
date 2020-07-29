package com.javafx;

import com.processing.PAnim;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import processing.javafx.PSurfaceFX;
import javafx.scene.input.MouseEvent;

import java.io.Console;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Communicates JavaFX events back to the running PApplet
 */
public class Controller implements Initializable {

    public static PSurfaceFX surface;
    public static PAnim p;
    public static Controller controller;
    protected static Stage stage;

    @FXML
    AnchorPane superParent;
    @FXML
    public Slider bgBrightness, penSize, frameNumberSlider,tweenEndFrameSlider;
    @FXML
    StackPane processing;
    @FXML
    ColorPicker colorPicker;
    @FXML
    CheckBox playbackCheckBox,showGizmosCheckBox;
    @FXML
    Button frameIncrementButton,frameDecrementButton,debugInfoButton,exportVideoButton;
    @FXML
    ScrollPane CanvasContainerScrollPane;
    @FXML
    public Canvas editorCanvas;
    public GraphicsContext editorGC;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Canvas canvas = (Canvas) surface.getNative();
        surface.fx.context = canvas.getGraphicsContext2D();
        processing.getChildren().add(canvas);
        canvas.widthProperty().bind(processing.widthProperty());
        canvas.heightProperty().bind(processing.heightProperty());
        controller=this;



        System.out.println(editorCanvas.widthProperty().getValue());
        editorGC=editorCanvas.getGraphicsContext2D();
//        editorGC.fillOval(0,0,editorCanvas.getWidth(),editorCanvas.getHeight());


//        CanvasContainerScrollPane.setHmax((12*(6+length)));
        CanvasContainerScrollPane.hmaxProperty().bind(editorCanvas.widthProperty());
        penSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            p.strokeWeight(newValue.intValue());
        });

        bgBrightness.valueProperty().addListener((observable, oldValue, newValue) -> {
            p.bgColor = newValue.intValue();
//            p.redraw();
            p.playbackInfo.updated();
        });
        frameNumberSlider.valueProperty().addListener((observable, oldValue, newValue)->{
//            System.out.println("Frame selected:"+newValue.intValue());
            p.playbackInfo.setFrame(newValue.intValue());
        });
        frameIncrementButton.setOnAction((e)->{
            p.playbackInfo.incFrame(1);
        });
        frameDecrementButton.setOnAction((e)->{
            p.playbackInfo.incFrame(-1);
        });
        exportVideoButton.setOnAction((e)->{
            try {
                p.exportScene();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        playbackCheckBox.setOnAction((e) -> {
            System.out.println("Playback clicked");
            if(playbackCheckBox.isSelected()){
//                p.playbackInfo.setFrame(frameNumberSlider.valueProperty().intValue());
                frameNumberSlider.setValue(p.playbackInfo.getFrame());
            }
            p.playbackInfo.setPlayback(playbackCheckBox.isSelected());
        });
        showGizmosCheckBox.setOnAction((e) -> {
            System.out.println("ShowGizmos clicked");
            p.playbackInfo.showGizmos=showGizmosCheckBox.isSelected();
            p.playbackInfo.updated();
        });

        tweenEndFrameSlider.valueProperty().addListener((observable, oldValue, newValue)->{
//            System.out.println("Frame selected:"+newValue.intValue());
            if(p.TweenEndRotationKeyframe !=null){
//                p.TweenEndRotationKeyframe.stateChange((keyframe)->{keyframe.setFrameStart(25);});
                p.TweenEndRotationKeyframe.stateChange((keyframe)->{keyframe.setFrameStart(newValue.intValue());});
                p.playbackInfo.updated();
                System.out.println("Tween end keyframe changed, new framestart:"+p.TweenEndRotationKeyframe.getFrameStart());
            }
        });
        debugInfoButton.setOnAction((e)->{
            p.debugInfo();
        });
    }

    @FXML
    private void redPen() {
        p.stroke(255, 0, 0);
    }

    @FXML
    private void greenPen() {
        p.stroke(0, 255, 0);
    }

    @FXML
    private void bluePen() {
        p.stroke(0, 0, 255);
    }

    @FXML
    private void exit() {
        stage.close();
    }

    @FXML
    private void clearCanvas() {
        p.redraw();
    }

    @FXML
    private void pickColor() {
        p.stroke((int) (colorPicker.getValue().getRed() * 255), (int) (colorPicker.getValue().getGreen() * 255),
                (int) (colorPicker.getValue().getBlue() * 255));
    }
}