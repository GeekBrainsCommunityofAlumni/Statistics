package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.interfaces.impls.PersonList;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import org.springframework.web.client.HttpClientErrorException;

public class PersonListController extends ListController {

    private ListInterface personList = new PersonList(URL);

    @FXML
    protected void initialize() {
        super.initialize(PERSON_TITLE);
        dataTableView.setItems(personList.getList());
        initListeners();
        deleteController.setPersonList(personList);
        setActivityButtons(personList);
    }

    private void initListeners() {
        personList.getList().addListener((ListChangeListener<ModelListData>) c -> {
            updateListCount(personList);
            setActivityButtons(personList);
            setFocus();
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !personList.getList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    @FXML
    protected void actionButtonAdd() {
        try {
            addController.setPerson(new Person(), this);
            if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
            addWindow.getStage().showAndWait();
            if (!addController.nameFieldIsEmpty(addController.getPerson().getName())) {
                personList.add(addController.getPerson());
            }
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        try {
            editController.setPerson((Person) dataTableView.getSelectionModel().getSelectedItem(), this);
            if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
            editWindow.getStage().showAndWait();
            ModelListData person = editController.getPerson();
            personList.update(person);
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonDelete() {
        try {
            deleteController.setPerson((Person) dataTableView.getSelectionModel().getSelectedItem(), this);
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
            personList.refreshList();
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    public ListInterface getPersonList() {
        return personList;
    }
}