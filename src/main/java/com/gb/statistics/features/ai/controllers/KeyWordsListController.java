package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.impls.KeyWordsList;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class KeyWordsListController {

    private final int CLICK_COUNT = 2;
    private final int MODAL_WIDTH = 300;
    private final int MODAL_HEIGHT = 100;
    private final String ADD_TITLE = "Создание записи";
    private final String EDIT_TITLE = "Редактирование записи";
    private final String DELETE_TITLE = "Удаление записи";
    private final String EMPTY_LIST_MESSAGE = "Список пуст";

    private PersonListInterface personList;
    private KeyWordsInterface keyWordsList = new KeyWordsList();

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
    private Button editButton;

    @FXML
    private Button deleteButton;

    private FXMLLoader loaderAddEdit = new FXMLLoader();
    private Parent parentEdit;
    private ModalKeyWordWindowController editKeyWordController;
    private FXMLLoader loaderDelete = new FXMLLoader();
    private Parent parentDelete;
    private DeleteKeyWordController deleteKeyWordController;
    private Stage modalEditWindowStage;
    private Stage modalDeleteWindowStage;
    private Stage mainStage;
    Label loadLabel = new Label("loading");

    @FXML
    private void initialize() {
        columnKeyWordName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        keyWordTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        keyWordTableView.setItems(keyWordsList.getKeyWordList());
        comboBoxPerson.setDisable(true);
        disableButtons(true);
        addButton.setDisable(true);
        initEditModalWindow();
        initDeleteModalWindow();
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

        personList.getPersonList().addListener((ListChangeListener<Person>) c -> {
            if (personList.getPersonList().size() == 0) {
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
                actionButtonEditKeyWord();
            }
        });

    }

    private void initComboBox() {
        comboBoxPerson.setPlaceholder(loadLabel);
        comboBoxPerson.setItems(personList.getPersonList());
        comboBoxPerson.getSelectionModel().selectFirst();
    }


    private void initEditModalWindow() {
        try {
            loaderAddEdit.setLocation(getClass().getResource("/fxml/modalEditKeyWordWindow.fxml"));
            parentEdit = loaderAddEdit.load();
            editKeyWordController = loaderAddEdit.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDeleteModalWindow() {
        try {
            loaderDelete.setLocation(getClass().getResource("/fxml/modalDeleteKeyWordWindow.fxml"));
            parentDelete = loaderDelete.load();
            deleteKeyWordController = loaderDelete.getController();
            deleteKeyWordController.setKeyWordList(keyWordsList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionButtonAddKeyWord() {
        editKeyWordController.setKeyWord(new KeyWord(), comboBoxPerson.getValue());
        showDialog(ADD_TITLE, modalEditWindowStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        if (!editKeyWordController.nameFieldIsEmpty(editKeyWordController.getKeyWord().getName())) {
            keyWordsList.addKeyWord(editKeyWordController.getKeyWord());
        }
    }

    @FXML
    private void actionButtonEditKeyWord() {
        editKeyWordController.setKeyWord(keyWordTableView.getSelectionModel().getSelectedItem(), comboBoxPerson.getValue());
        showDialog(EDIT_TITLE, modalEditWindowStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        System.out.println(editKeyWordController.getKeyWord());
        keyWordsList.updateKeyWord(editKeyWordController.getKeyWord());
    }

    @FXML
    private void actionButtonDeleteKeyWord() {
        deleteKeyWordController.setKeyWord(keyWordTableView.getSelectionModel().getSelectedItem());
        showDialog(DELETE_TITLE, modalDeleteWindowStage, parentDelete, MODAL_WIDTH, MODAL_HEIGHT);
    }

    private void showDialog(String title, Stage stage, Parent parent, int width, int height) {
        if (stage == null) {
            stage = new Stage();
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            switch (title) {
                case ADD_TITLE:
                    this.modalEditWindowStage = stage;
                    break;
                case EDIT_TITLE:
                    this.modalEditWindowStage = stage;
                    break;
                case DELETE_TITLE:
                    this.modalDeleteWindowStage = stage;
                    break;
            }
        }
        stage.setTitle(title);
        stage.showAndWait();
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
