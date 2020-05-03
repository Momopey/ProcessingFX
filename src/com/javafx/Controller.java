package com.javafx;

import com.processing.PAnim;
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
    CheckBox playbackCheckBox;
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


//        int length=100;
//        int height=6;
//        int numControllers=3;
//        editorCanvas.widthProperty().setValue((12*(6+length)));
//        editorGC.setFill(Color.WHITE);
//        editorGC.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        editorGC.setFill(new Color(0,0,0,0.03));
//        editorGC.fillRoundRect(0,0,12*(6+length),15+25*6+15,10,10);
//        editorGC.fillRoundRect(12*6,0,12*(6+length),15+25*numControllers,10,10);
//        editorGC.fillRoundRect(12*6,0,12*(6+length),15+25*height+15,10,10);
//        editorGC.fillRoundRect(0,15,12*(6+length),15+25*6,10,10);
//        editorGC.setFont(new Font(12));
//        editorGC.setFill(Color.BLACK);
//        editorGC.fillText("Hello world",8,11);
//        editorGC.setLineWidth(1);
//        editorGC.setFont(new Font(10));
//        for(int i=0;i<length;i++) {
//            editorGC.setFill(new Color(0,0,0,0.4));
//            editorGC.fillText(String.valueOf(i%10),12*(height+i)+1,11);
//            if(i%5==0){
//                editorGC.setFill(new Color(0,0,0,0.06));
//                editorGC.fillRect(12 * (6 + i), 15,12,25 * height);
//            }else{
//                editorGC.setStroke(new Color(0,0,0,0.06));
//                editorGC.strokeLine(12 * (6 + i), 15, 12 * (6 + i), 15 + 25 * height);
//            }
//        }
//        editorGC.setStroke(new Color(0,0,0,0.06));
//        for(int i=0;i<height+1;i++){
//            editorGC.strokeLine(12 * (1), 15+25*i, 12 * (6 + length), 15+25*i);
//        }
//        editorGC.setStroke(new Color(0,0,0,0.1));
//        editorGC.strokeLine(12 * (1), 15+25*numControllers, 12 * (6 + length), 15+25*numControllers);
//        editorGC.setFill(new Color(0.72,0.7,0.7,0.97));
//        editorGC.setStroke(new Color(0.4,0.4,0.4,0.90));

//        int x=2;
//        int y=1;
//        int w=12;
//        editorGC.fillRect(12*(6+x)+1,15+25*(y)+1,12*w,25-2);
//        editorGC.strokeRect(12*(6+x)+1,15+25*(y)+1,12*w,25-2);
//        //Resolved icon
//        editorGC.strokeOval(12*(6+x)+4,15+25*(y)+4,6,6);
//        editorGC.strokeLine(12*(6+x)+4,15+25*(y)+4,12*(6+x)+4+6,15+25*(y)+4+6);
//        editorGC.strokeLine(12*(6+x)+4+6,15+25*(y)+4,12*(6+x)+4,15+25*(y)+4+6);
//        //keyframe icon
//        editorGC.strokeRect(12*(6+x)+3,15+25*(y)+10+3,8,8);
//        //Length icons
//        if(w>=3) {
//            editorGC.strokeRect(12 * (6 + x) + 10 + 4 + 18 + 3, 15 + 25 * (y) + 4, 12 * w - 10 - 4 - 18 - 3 - 3, 8);//for when special icon is shown
//        }else if(w>=1){
//            editorGC.strokeRect(12*(6+x)+10+4,15+25*(y)+4,12*w-10-4-3,8);// otherwise
//        }
//        //Special icon
//        if(w>=3) {
//            editorGC.strokeRect(12 * (6 + x) + 10 + 4, 15 + 25 * (y) + 3, 18, 18);
//        }


        penSize.valueProperty().addListener((observable, oldValue, newValue) -> {
            p.strokeWeight(newValue.intValue());
        });

        bgBrightness.valueProperty().addListener((observable, oldValue, newValue) -> {
            p.bgColor = newValue.intValue();
            p.redraw();
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
            p.exportScene();
        });
        playbackCheckBox.setOnAction((e) -> {
            System.out.println("Playback clicked");
            if(playbackCheckBox.isSelected()){
//                p.playbackInfo.setFrame(frameNumberSlider.valueProperty().intValue());
                frameNumberSlider.setValue(p.playbackInfo.getFrame());
            }
            p.playbackInfo.setPlayback(playbackCheckBox.isSelected());
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