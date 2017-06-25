package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.interfaces.impls.KeyWordsList;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.web.client.HttpClientErrorException;

public class KeyWordsListController extends ListController {

    private ListInterface personList;
    private ListInterface keyWordsList = new KeyWordsList(URL);

    @FXML
    private ComboBox<Person> comboBoxPerson;

    @FXML
    private Button addButton;

    @FXML
    protected void initialize() {
        super.initialize(KEYWORD_TITLE);
        dataTableView.setItems(keyWordsList.getList());
        disableButtons(true);
        addButton.setDisable(true);
        deleteController.setKeyWordList(keyWordsList);
    }

    private void initListeners() {
        comboBoxPerson.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ((KeyWordsList)keyWordsList).setPerson(newValue);
                keyWordsList.refreshList();
            } else {
                comboBoxPerson.getSelectionModel().selectFirst();
                keyWordsList.getList().clear();
            }
        });

        personList.getList().addListener((ListChangeListener<Person>) c -> {
            if (personList.getList().size() == 0) {
                addButton.setDisable(true);
            } else {
                addButton.setDisable(false);
                comboBoxPerson.getSelectionModel().selectFirst();
            }
        });
    }

    public void setPersonList(ListInterface personList) {
        this.personList = personList;
        initListeners();
        initComboBox();

        keyWordsList.getList().addListener((ListChangeListener<ModelListData>) c -> {
            updateListCount(keyWordsList);
            setActivityButtons(keyWordsList);
            setFocus();
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !keyWordsList.getList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    private void initComboBox() {
        comboBoxPerson.setItems(personList.getList());
        comboBoxPerson.getSelectionModel().selectFirst();
    }

    @FXML
    protected void actionButtonAdd() {
        try {
            addController.setKeyWord(new KeyWord(), comboBoxPerson.getValue(), this);
            if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
            addWindow.getStage().showAndWait();
            if (!addController.nameFieldIsEmpty(addController.getKeyWord().getName())) {
                keyWordsList.add(addController.getKeyWord());
            }
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        try {
            editController.setKeyWord(dataTableView.getSelectionModel().getSelectedItem(), comboBoxPerson.getValue(), this);
            if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
            editWindow.getStage().showAndWait();
            keyWordsList.update(editController.getKeyWord());
            visibleErrorMessage(false);
        } catch (HttpClientErrorException e) {
            setErrorMessage(e.getMessage());
        }
    }

    @FXML
    protected void actionButtonDelete() {
        try {
            deleteController.setKeyWord((KeyWord) dataTableView.getSelectionModel().getSelectedItem(), this);
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
}