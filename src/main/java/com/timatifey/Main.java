package com.timatifey;

import com.timatifey.controller.MainScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        URL url = getClass().getResource("/mainScene.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        MainScene contMenu = loader.getController();
        contMenu.setStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Maze Solver");
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}

