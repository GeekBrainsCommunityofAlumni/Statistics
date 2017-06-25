package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.interfaces.impls.SiteList;
import com.gb.statistics.features.ai.model.Site;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import org.springframework.web.client.HttpClientErrorException;

public class SiteListController extends ListController {

    private ListInterface siteList = new SiteList(URL);

    @FXML
    protected void initialize() {
        super.initialize(SITE_TITLE);
        dataTableView.setItems(siteList.getList());
        initListeners();
        deleteController.setSiteList(siteList);
        setActivityButtons(siteList);
    }

    private void initListeners() {
        siteList.getList().addListener((ListChangeListener<Site>) c -> {
            updateListCount(siteList);
            setActivityButtons(siteList);
            setFocus();
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !siteList.getList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    @FXML
    protected void actionButtonAdd() {
        try {
            addController.setSite(new Site(), this);
            if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
            addWindow.getStage().showAndWait();
            if (!addController.nameFieldIsEmpty(addController.getSite().getName())) {
                siteList.add(addController.getSite());
            }
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        try {
            editController.setSite((Site) dataTableView.getSelectionModel().getSelectedItem(), this);
            if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
            editWindow.getStage().showAndWait();
            siteList.update(editController.getSite());
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonDelete() {
        try {
            deleteController.setSite((Site) dataTableView.getSelectionModel().getSelectedItem(), this);
            if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
            deleteWindow.getStage().showAndWait();
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonRefresh() {
        try {
            siteList.refreshList();
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    public ListInterface getSiteList() {
        return siteList;
    }
}