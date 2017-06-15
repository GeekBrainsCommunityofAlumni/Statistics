package com.gb.statistics.features.ai.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import java.io.IOException;

public class RootFrameController {

    private FXMLLoader loaderPersonList = new FXMLLoader();
    private PersonListController personListController;
    private Parent parentRoot;

    @FXML
    private TabPane rootTabPane;

    @FXML
    private Tab personListTab;

    @FXML
    private void initialize() {
        initPersonList();
        personListTab.setContent(parentRoot);
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    private void initPersonList() {
        try {
            loaderPersonList.setLocation(getClass().getResource("/fxml/personListWindow.fxml"));
            parentRoot = loaderPersonList.load();
            personListController = loaderPersonList.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainStage(Stage mainStage) {
        personListController.setMainStage(mainStage);
    }
}
