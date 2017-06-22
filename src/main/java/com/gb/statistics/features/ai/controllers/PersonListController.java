package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.impls.PersonList;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class PersonListController extends ListController {

    private PersonListInterface personList;

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> columnPersonName;

    @FXML
    private Label personListCount;

    @FXML
    protected void initialize() {
        super.initialize();
        personList = new PersonList();
        columnPersonName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        personTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        personTableView.setItems(personList.getPersonList());
        initListeners();
        deleteController.setPersonList(personList);
        setActivityButtons();
    }

    private void initListeners() {
        personList.getPersonList().addListener((ListChangeListener<Person>) c -> {
            updatePersonListCount();
            setActivityButtons();
            setFocus();
        });

        personTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !personList.getPersonList().isEmpty()) {
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
            personList.addPerson(addController.getPerson());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        editController.setPerson(personTableView.getSelectionModel().getSelectedItem(), this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        personList.updatePerson(personTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    protected void actionButtonDelete() {
        deleteController.setPerson(personTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
    }

    private void updatePersonListCount() {
        personListCount.setText(String.valueOf(personList.getPersonList().size()));
    }

    private void setActivityButtons() {
        if (personList.getPersonList().size() == 0) disableButtons(true);
        else disableButtons(false);
    }

    private void disableButtons(boolean value) {
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    private void setFocus() {
        personTableView.getSelectionModel().select(0);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public PersonListInterface getPersonList() {
        return personList;
    }
}