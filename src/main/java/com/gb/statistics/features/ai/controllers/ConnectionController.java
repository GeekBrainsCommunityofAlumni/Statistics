package com.gb.statistics.features.ai.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class ConnectionController {

    private Stage stage;
    private Scene connectScene;
    private Scene scene;
    private RootFrameController rootFrameController;

    @FXML
    private Button connectButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/rootTabPane.fxml"));
        Parent parent = loader.load();
        rootFrameController = loader.getController();
        rootFrameController.setMainStage(stage);
        scene = new Scene(parent);
        rootFrameController.setConnectiontController(this);
    }

    public void setMainStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initTabFrame() {
        //stage.hide();
        errorLabel.setVisible(false);
        stage.setScene(scene);
        //stage.show();
        rootFrameController.loadData();
    }

    public void disconnect() {
        stage.setScene(connectScene);
        errorLabel.setVisible(true);
    }

    public void setConnectScene(Scene connectScene) {
        this.connectScene = connectScene;
    }
}
