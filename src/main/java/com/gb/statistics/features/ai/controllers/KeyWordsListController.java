package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
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

    Label loadLabel = new Label("loading");

    @FXML
    private ComboBox<Person> comboBoxPerson;

    @FXML
    private TableView<KeyWord> keyWordTableView;

    @FXML
    private TableColumn<KeyWord, String> columnKeyWordName;

    @FXML
    private Label keyWordsListCount;

    @FXML
    private Button addButton;

    @FXML
    protected void initialize() {
        super.initialize();
        columnKeyWordName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        keyWordTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        keyWordTableView.setItems(keyWordsList.getKeyWordList());
        comboBoxPerson.setDisable(true);
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
                comboBoxPerson.getSelectionModel().select(-1);
                keyWordsList.getKeyWordList().clear();
            }
        });

        personList.getSiteList().addListener((ListChangeListener<Person>) c -> {
            if (personList.getSiteList().size() == 0) {
                comboBoxPerson.getSelectionModel().select(-1);
                comboBoxPerson.setDisable(true);
                addButton.setDisable(true);

            } else {
                comboBoxPerson.setDisable(false);
                addButton.setDisable(false);
                comboBoxPerson.getSelectionModel().selectFirst();
            }
        });
    }

    public void setPersonList(PersonListInterface personList) {
        this.personList = personList;
        initListeners();
        initComboBox();

        keyWordsList.getKeyWordList().addListener((ListChangeListener<KeyWord>) c -> {
            updateKeyWordsListCount();
            setActivityButtons();
            //setFocus();
        });

        keyWordTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !keyWordsList.getKeyWordList().isEmpty()) {
                actionButtonEdit();
            }
        });

    }

    private void initComboBox() {
        comboBoxPerson.setPlaceholder(loadLabel);
        comboBoxPerson.setItems(personList.getSiteList());
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
        editController.setKeyWord(keyWordTableView.getSelectionModel().getSelectedItem(), comboBoxPerson.getValue(), this);
        if (editWindow == null) editWindow = new ModalWindow(EDIT_TITLE, mainStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        editWindow.getStage().showAndWait();
        keyWordsList.updateKeyWord(editController.getKeyWord());
    }

    @FXML
    protected void actionButtonDelete() {
        deleteController.setKeyWord(keyWordTableView.getSelectionModel().getSelectedItem(), this);
        if (deleteWindow == null) deleteWindow = new ModalWindow(DELETE_TITLE, mainStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
        deleteWindow.getStage().showAndWait();
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
        if (keyWordsList.getKeyWordList().size() == 1) keyWordTableView.getSelectionModel().select(0);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public PersonListInterface getPersonList() {
        return personList;
    }
}