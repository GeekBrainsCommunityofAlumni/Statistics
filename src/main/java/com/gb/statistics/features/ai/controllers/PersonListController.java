package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.interfaces.impls.PersonList;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;

public class PersonListController extends ListController {

    private ListInterface personList = new PersonList(URL);

    @FXML
    protected void initialize() throws Exception {
        super.initialize(PERSON_TITLE);
        personList.setController(this);
        dataTableView.setItems(personList.getList());
        initListeners();
        initFilter(personList);
        deleteController.setPersonList(personList);
        setActivityButtons(personList);
    }

    private void initListeners() {
        personList.getList().addListener((ListChangeListener<ModelListData>) c -> {
            updateListCount(personList);
            setActivityButtons(personList);
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !personList.getList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    @FXML
    protected void actionButtonAdd() {
        addController.setPerson(new Person(), this);
        if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
        addWindow.getStage().showAndWait();
        if (!addController.nameFieldIsEmpty(addController.getPerson().getName())) {
            personList.add(addController.getPerson());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        Person currentPerson = (Person)dataTableView.getSelectionModel().getSelectedItem();
        String currentPersonName = currentPerson.getName();
        editController.setPerson(currentPerson, this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        ModelListData updatedPerson = editController.getPerson();
        if (!currentPersonName.equals(updatedPerson.getName())) personList.update(updatedPerson);
    }

    @FXML
    protected void actionButtonDelete() {
        deleteController.setPerson((Person)dataTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
    }

    @FXML
    protected void actionButtonRefresh() {
        try {
            personList.refreshList();
        } catch (RuntimeException e) {

        }
    }

    public ListInterface getPersonList() {
        return personList;
    }
}