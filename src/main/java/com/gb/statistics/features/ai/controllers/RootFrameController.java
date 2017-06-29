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
    private FXMLLoader loaderSiteList = new FXMLLoader();
    private PersonListController personListController;
    private KeyWordsListController keyWordsListController;
    private SiteListController siteListController;
    private Parent parentPersonList;
    private Parent parentKeyWordsList;
    private Parent parentSiteList;

    @FXML
    private TabPane rootTabPane;

    @FXML
    private Tab personListTab;

    @FXML
    private Tab keyWordsListTab;

    @FXML
    private Tab siteListTab;

    @FXML
    private void initialize() throws IOException {
        initPersonList();
        initKeyWordsList();
        initSiteList();
        personListTab.setContent(parentPersonList);
        keyWordsListTab.setContent(parentKeyWordsList);
        siteListTab.setContent(parentSiteList);
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    private void initPersonList() throws IOException {
        loaderPersonList.setLocation(getClass().getResource("/fxml/personAndSiteListWindow.fxml"));
        loaderPersonList.setController(new PersonListController());
        parentPersonList = loaderPersonList.load();
        personListController = loaderPersonList.getController();
    }

    private void initKeyWordsList() throws IOException {
        loaderKeyWordsList.setLocation(getClass().getResource("/fxml/keyWordsListWindow.fxml"));
        loaderKeyWordsList.setController(new KeyWordsListController());
        parentKeyWordsList = loaderKeyWordsList.load();
        keyWordsListController = loaderKeyWordsList.getController();
        keyWordsListController.setPersonList(personListController.getPersonList());
    }

    private void initSiteList() throws IOException {
        loaderSiteList.setLocation(getClass().getResource("/fxml/personAndSiteListWindow.fxml"));
        loaderSiteList.setController(new SiteListController());
        parentSiteList = loaderSiteList.load();
        siteListController = loaderSiteList.getController();
    }

    public void setMainStage(Stage mainStage) {
        personListController.setMainStage(mainStage);
        keyWordsListController.setMainStage(mainStage);
        siteListController.setMainStage(mainStage);
    }

    public void loadData() {
        personListController.getPersonList().refreshList();
        siteListController.getSiteList().refreshList();
    }
}