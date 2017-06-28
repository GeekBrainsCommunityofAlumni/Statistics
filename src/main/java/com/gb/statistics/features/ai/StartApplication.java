package com.gb.statistics.features.ai;

import com.gb.statistics.features.ai.controllers.ConnectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StartApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Image ico = new Image("/images/icon.jpg");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/connectionFrame.fxml"));
        Parent main = loader.load();
        ConnectionController connectionController = loader.getController();
        connectionController.setMainStage(primaryStage);

        primaryStage.setTitle("GBCA Statistics - Панель администрирования");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(main, 300, 300));
        primaryStage.getIcons().add(ico);
        primaryStage.show();
    }
}