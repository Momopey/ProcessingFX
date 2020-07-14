package com.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    }
}
