package com.gb.statistics.features.ai.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;

public class ConnectionController {

    private static Stage stage;
    private static Scene connectScene;
    private Scene scene;
    private RootFrameController rootFrameController;

    @FXML
    private Button connectButton;

    @FXML
    private static Label errorLabel = new Label();

    @FXML
    private void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/rootTabPane.fxml"));
        Parent parent = loader.load();
        rootFrameController = loader.getController();
        rootFrameController.setMainStage(stage);
        scene = new Scene(parent);
        errorLabel.setVisible(false);
        System.out.println("visible");
    }

    public void setMainStage(Stage stage) {
        ConnectionController.stage = stage;
    }

    @FXML
    public void initTabFrame() {
        stage.hide();
        stage.setScene(scene);
        stage.show();
        rootFrameController.loadData();
    }

    public static void disconnect() {
        stage.setScene(connectScene);
        //errorLabel.setVisible(true);
    }

    public static void setConnectScene(Scene connectScene) {
        ConnectionController.connectScene = connectScene;
    }
}
