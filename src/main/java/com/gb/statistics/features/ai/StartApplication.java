package com.gb.statistics.features.ai;

import com.gb.statistics.features.ai.controllers.RootFrameController;
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
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/rootTabPane.fxml"));
        Parent main = loader.load();
        RootFrameController rootFrameController = loader.getController();
        rootFrameController.setMainStage(primaryStage);

        primaryStage.setTitle("Панель администрирования");
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(main, 300, 300));
        primaryStage.show();
    }
}