package com.gb.statistics.features.ai;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/personlist.fxml"));
        primaryStage.setTitle("Панель администрирования");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();
    }
}
