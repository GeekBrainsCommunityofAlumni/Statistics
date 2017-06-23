package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.SiteListInterface;
import com.gb.statistics.features.ai.interfaces.impls.SiteList;
import com.gb.statistics.features.ai.model.Site;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class SiteListController extends ListController {

    private SiteListInterface siteList;

    @FXML
    private TableView<Site> dataTableView;

    @FXML
    private TableColumn<Site, String> columnPersonName;

    @FXML
    private Label personListCount;

    @FXML
    protected void initialize() {
        super.initialize(SITE_TITLE);
        siteList = new SiteList();
        columnPersonName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        dataTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        dataTableView.setItems(siteList.getSiteList());
        initListeners();
        deleteController.setSiteList(siteList);
        setActivityButtons();
    }

    private void initListeners() {
        siteList.getSiteList().addListener((ListChangeListener<Site>) c -> {
            updatePersonListCount();
            setActivityButtons();
            setFocus();
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !siteList.getSiteList().isEmpty()) {
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
            siteList.addSite(addController.getSite());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        editController.setSite(dataTableView.getSelectionModel().getSelectedItem(), this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        siteList.updateSite(dataTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    protected void actionButtonDelete() {
        deleteController.setSite(dataTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
    }

    @Override
    public PersonListInterface getPersonList() {
        return null;
    }

    private void updatePersonListCount() {
        personListCount.setText(String.valueOf(siteList.getSiteList().size()));
    }

    private void setActivityButtons() {
        if (siteList.getSiteList().size() == 0) disableButtons(true);
        else disableButtons(false);
    }

    private void disableButtons(boolean value) {
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    private void setFocus() {
        dataTableView.getSelectionModel().select(0);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public SiteListInterface getSiteList() {
        return siteList;
    }
}