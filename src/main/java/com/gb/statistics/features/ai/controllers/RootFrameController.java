package com.gb.statistics.features.ai.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.springframework.web.client.HttpClientErrorException;
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
    private void initialize() {
        initPersonList();
        initKeyWordsList();
        initSiteList();
        personListTab.setContent(parentPersonList);
        keyWordsListTab.setContent(parentKeyWordsList);
        siteListTab.setContent(parentSiteList);
        rootTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        try {
            personListController.getPersonList().refreshList();
            personListController.visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            personListController.setErrorMessage(e.getMessage());
        }

        try {
            siteListController.getSiteList().refreshList();
            siteListController.visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            siteListController.setErrorMessage(e.getMessage());
        }
    }

    private void initPersonList() {
        try {
            loaderPersonList.setLocation(getClass().getResource("/fxml/personAndSiteListWindow.fxml"));
            loaderPersonList.setController(new PersonListController());
            parentPersonList = loaderPersonList.load();
            personListController = loaderPersonList.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initKeyWordsList() {
        try {
            loaderKeyWordsList.setLocation(getClass().getResource("/fxml/keyWordsListWindow.fxml"));
            loaderKeyWordsList.setController(new KeyWordsListController());
            parentKeyWordsList = loaderKeyWordsList.load();
            keyWordsListController = loaderKeyWordsList.getController();
            keyWordsListController.setPersonList(personListController.getPersonList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initSiteList() {
        try {
            loaderSiteList.setLocation(getClass().getResource("/fxml/personAndSiteListWindow.fxml"));
            loaderSiteList.setController(new SiteListController());
            parentSiteList = loaderSiteList.load();
            siteListController = loaderSiteList.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainStage(Stage mainStage) {
        personListController.setMainStage(mainStage);
        keyWordsListController.setMainStage(mainStage);
        siteListController.setMainStage(mainStage);
    }
}