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
    private boolean connect = false;

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
        scene = new Scene(parent);
        rootFrameController.setConnectionController(this);
    }

    public void setMainStage(Stage stage) {
        this.stage = stage;
        rootFrameController.setMainStage(stage);
    }

    @FXML
    public void initTabFrame() {
        connectButton.setDisable(true);
        connect = true;
        rootFrameController.loadData();

        if (connect) {
            errorLabel.setVisible(false);
            stage.setScene(scene);
            stage.setMinWidth(601);
            stage.setResizable(true);
        }
    }

    public void disconnect() {
        stage.setScene(connectScene);
        stage.setMinWidth(600);
        stage.setResizable(false);
        errorLabel.setVisible(true);
        connect = false;
        connectButton.setDisable(false);
    }

    public void setConnectScene(Scene connectScene) {
        this.connectScene = connectScene;
    }
}
