package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.impls.KeyWordsList;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.window.ModalWindow;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class KeyWordsListController extends ListController {

    private PersonListInterface personList;
    private KeyWordsInterface keyWordsList = new KeyWordsList();

    @FXML
    private ComboBox<Person> comboBoxPerson;

    @FXML
    private TableColumn<ModelListData, String> columnKeyWordName;

    @FXML
    private Label keyWordsListCount;

    @FXML
    private Button addButton;

    @FXML
    protected void initialize() {
        super.initialize(KEYWORD_TITLE);
        columnKeyWordName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());

        dataTableView.setItems(keyWordsList.getKeyWordList());
        disableButtons(true);
        addButton.setDisable(true);
        deleteController.setKeyWordList(keyWordsList);
    }

    private void initListeners() {
        comboBoxPerson.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                keyWordsList.setPerson(newValue);
                keyWordsList.refreshKeyWordList();
            } else {
                comboBoxPerson.getSelectionModel().selectFirst();
                keyWordsList.getKeyWordList().clear();
            }
        });

        personList.getPersonList().addListener((ListChangeListener<Person>) c -> {
            if (personList.getPersonList().size() == 0) {
                addButton.setDisable(true);
            } else {
                addButton.setDisable(false);
                comboBoxPerson.getSelectionModel().selectFirst();
            }
        });
    }

    public void setPersonList(PersonListInterface personList) {
        this.personList = personList;
        initListeners();
        initComboBox();

        keyWordsList.getKeyWordList().addListener((ListChangeListener<ModelListData>) c -> {
            updateKeyWordsListCount();
            setActivityButtons();
            setFocus();
        });

        dataTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !keyWordsList.getKeyWordList().isEmpty()) {
                actionButtonEdit();
            }
        });
    }

    private void initComboBox() {
        comboBoxPerson.setItems(personList.getPersonList());
        comboBoxPerson.getSelectionModel().selectFirst();
    }

    @FXML
    protected void actionButtonAdd() {
        addController.setKeyWord(new KeyWord(), comboBoxPerson.getValue(), this);
        if (addWindow == null) addWindow = new ModalWindow(ADD_TITLE, mainStage, parentAdd, MODAL_WIDTH, MODAL_HEIGHT);
        addWindow.getStage().showAndWait();
        if (!addController.nameFieldIsEmpty(addController.getKeyWord().getName())) {
            keyWordsList.addKeyWord(addController.getKeyWord());
        }
    }

    @FXML
    protected void actionButtonEdit() {
        editController.setKeyWord((KeyWord) dataTableView.getSelectionModel().getSelectedItem(), comboBoxPerson.getValue(), this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        keyWordsList.updateKeyWord(editController.getKeyWord());
    }

    @FXML
    protected void actionButtonDelete() {
        deleteController.setKeyWord((KeyWord) dataTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
    }

    @Override
    public PersonListInterface getPersonList() {
        return null;
    }

    private void updateKeyWordsListCount() {
        keyWordsListCount.setText(String.valueOf(keyWordsList.getKeyWordList().size()));
    }

    private void setActivityButtons() {
        if (keyWordsList.getKeyWordList().isEmpty()) disableButtons(true);
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
}