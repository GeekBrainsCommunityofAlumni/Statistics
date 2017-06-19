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
    private FXMLLoader loaderKeyWordsList = new FXMLLoader();
    private PersonListController personListController;
    private KeyWordsListController keyWordsListController;
    private Parent parentPersonList;
    private Parent parentKeyWordsList;

    @FXML
    private TabPane rootTabPane;

    @FXML
    private Tab personListTab;

    @FXML
    private Tab keyWordsListTab;

    @FXML
    private void initialize() {
        initPersonList();
        initKeyWordsList();
        personListController.setKeyWordsListController(keyWordsListController);



        personListTab.setContent(parentPersonList);
        keyWordsListTab.setContent(parentKeyWordsList);
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    private void initPersonList() {
        try {
            loaderPersonList.setLocation(getClass().getResource("/fxml/personListWindow.fxml"));
            parentPersonList = loaderPersonList.load();
            personListController = loaderPersonList.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initKeyWordsList() {
        try {
            loaderKeyWordsList.setLocation(getClass().getResource("/fxml/keyWordsListWindow.fxml"));
            parentKeyWordsList = loaderKeyWordsList.load();
            keyWordsListController = loaderKeyWordsList.getController();
            keyWordsListController.setPersonList(personListController.getPersonList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainStage(Stage mainStage) {
        personListController.setMainStage(mainStage);
        keyWordsListController.setMainStage(mainStage);
    }
}
