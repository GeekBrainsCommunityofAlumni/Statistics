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

public class KeyWordsListController extends ListController {

    private ListInterface personList;
    private ListInterface keyWordsList = new KeyWordsList(URL);

    @FXML
    private ComboBox<Person> comboBoxPerson;

    @FXML
    private Button addButton;

    @FXML
    protected void initialize() throws Exception {
        super.initialize(KEYWORD_TITLE);
        keyWordsList.setController(this);
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
            } else keyWordsList.refreshList();
        });

        personList.getList().addListener((ListChangeListener<ModelListData>) c -> {
            if (personList.getList().size() == 0) {
                addButton.setDisable(true);
                comboBoxPerson.getSelectionModel().clearSelection();
            } else {
                addButton.setDisable(false);
                comboBoxPerson.getSelectionModel().selectFirst();
            }
        });

        keyWordsList.getList().addListener((ListChangeListener<ModelListData>) c -> {
            updateListCount(keyWordsList);
            setActivityButtons(keyWordsList);
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !keyWordsList.getList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    public void setPersonList(ListInterface personList) {
        this.personList = personList;
        initComboBox();
        initListeners();
        initFilter(keyWordsList);
    }

    private void initComboBox() {
        comboBoxPerson.setItems(personList.getList());
        comboBoxPerson.getSelectionModel().selectFirst();
    }

    @FXML
    protected void actionButtonAdd() {
        visibleErrorMessage(false);
        addController.setKeyWord(new KeyWord(), comboBoxPerson.getValue(), this);
        if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
        addWindow.getStage().showAndWait();
        if (!addController.nameFieldIsEmpty(addController.getKeyWord().getName())) {
            keyWordsList.add(addController.getKeyWord());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        visibleErrorMessage(false);
        KeyWord currentKeyWord = (KeyWord) dataTableView.getSelectionModel().getSelectedItem();
        String currentKeyWordName = currentKeyWord.getName();
        editController.setKeyWord(dataTableView.getSelectionModel().getSelectedItem(), comboBoxPerson.getValue(), this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        ModelListData updatedKeyWord = editController.getKeyWord();
        if (!currentKeyWordName.equals(updatedKeyWord.getName())) keyWordsList.update(updatedKeyWord);
    }

    @FXML
    protected void actionButtonDelete() {
        visibleErrorMessage(false);
        deleteController.setKeyWord((KeyWord) dataTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
    }

    @FXML
    protected void actionButtonRefresh() {
        visibleErrorMessage(false);
        personList.refreshList();
    }

    public void refreshPersonList() {
        personList.refreshList();
    }
}