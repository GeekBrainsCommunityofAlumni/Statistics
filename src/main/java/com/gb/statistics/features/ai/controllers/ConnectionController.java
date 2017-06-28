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
    private Parent parent;
    private RootFrameController rootFrameController;

    @FXML
    private Button connectButton;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() throws IOException {
        errorLabel.setVisible(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/rootTabPane.fxml"));
        parent = loader.load();
        rootFrameController = loader.getController();
        rootFrameController.setMainStage(stage);
    }

    public void setMainStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initTabFrame() {
        rootFrameController.loadData();
        stage.setScene(new Scene(parent));
    }
}
