package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.interfaces.impls.PersonList;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;

public class PersonListController extends ListController {

    private ListInterface personList = new PersonList(URL);

    @FXML
    private TextField search;

    @FXML
    protected void initialize() {
        super.initialize(PERSON_TITLE);
        personList.setController(this);
        dataTableView.setItems(personList.getList());
        initListeners();
        initFilter();
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

    private void initFilter() {
        search.textProperty().addListener(o -> {
            if(search.textProperty().get().isEmpty()) {
                dataTableView.setItems(personList.getList());
                return;
            }
            ObservableList<ModelListData> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<ModelListData, ?>> cols = dataTableView.getColumns();
            for(int i=0; i<personList.getList().size(); i++) {
                for(int j=0; j<cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(personList.getList().get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if(cellValue.contains(search.textProperty().get().toLowerCase())) {
                        tableItems.add((ModelListData) personList.getList().get(i));
                        break;
                    }
                }
            }
            dataTableView.setItems(tableItems);
        });
    }
}