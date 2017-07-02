package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.interfaces.impls.SiteList;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Site;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;

public class SiteListController extends ListController {

    private ListInterface siteList = new SiteList(URL);

    @FXML
    protected void initialize() {
        super.initialize(SITE_TITLE);
        siteList.setController(this);
        dataTableView.setItems(siteList.getList());
        initListeners();
        initFilter(siteList);
        deleteController.setSiteList(siteList);
        setActivityButtons(siteList);
    }

    private void initListeners() {
        siteList.getList().addListener((ListChangeListener<Site>) c -> {
            updateListCount(siteList);
            setActivityButtons(siteList);
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !siteList.getList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    @FXML
    protected void actionButtonAdd() {
        addController.setSite(new Site(), this);
        if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
        addWindow.getStage().showAndWait();
        if (!addController.nameFieldIsEmpty(addController.getSite().getName())) {
            siteList.add(addController.getSite());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        Site currentSite = (Site)dataTableView.getSelectionModel().getSelectedItem();
        String currentSiteName = currentSite.getName();
        editController.setSite((Site) dataTableView.getSelectionModel().getSelectedItem(), this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        ModelListData updatedSite = editController.getSite();
        if (!currentSiteName.equals(updatedSite.getName())) siteList.update(updatedSite);
    }

    @FXML
    protected void actionButtonDelete() {
        deleteController.setSite((Site) dataTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
    }

    @FXML
    protected void actionButtonRefresh() {
        siteList.refreshList();
    }

    public ListInterface getSiteList() {
        return siteList;
    }
}