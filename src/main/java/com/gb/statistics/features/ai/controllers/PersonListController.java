package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.impls.FakePersonList;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class PersonListController {

    private final int CLICK_COUNT = 2;
    private final int MODAL_WIDTH = 300;
    private final int MODAL_HEIGHT = 100;
    private final String ADD_TITLE = "Создание записи";
    private final String EDIT_TITLE = "Редактирование записи";
    private final String DELETE_TITLE = "Удаление записи";
    private final String EMPTY_LIST_MESSAGE = "Список пуст";

    private PersonListInterface personList = new FakePersonList();
    private Parent parentEdit;
    private Parent parentDelete;
    private FXMLLoader loaderAddEdit = new FXMLLoader();
    private FXMLLoader loaderDelete = new FXMLLoader();
    private ModalPersonWindowController editPersonController;
    private DeletePersonController deletePersonController;
    private KeyWordsListController keyWordsListController;
    private Stage modalEditWindowStage;
    private Stage modalDeleteWindowStage;
    private Stage mainStage;

    @FXML
    private TableView<Person> personTableView;

    @FXML
    private TableColumn<Person, String> columnPersonName;

    @FXML
    private Label personListCount;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        columnPersonName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        personTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        personTableView.setItems(personList.getPersonList());
        initListeners();
        initEditModalWindow();
        initDeleteModalWindow();
        addFakeData();
        personList.getPersonList();
    }

    private void addFakeData() {
        personList.addPerson(new Person(1, "Путин"));
        personList.addPerson(new Person(2, "Медведев"));
        personList.addPerson(new Person(3, "Навальный"));
        personList.addPerson(new Person(4, "Жириновский"));
    }

    private void initListeners() {
        personList.getPersonList().addListener((ListChangeListener<Person>) c -> {
            updatePersonListCount();
            setActivityButtons();
            setFocus();
        });

        personTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !personList.getPersonList().isEmpty()) {
                actionButtonEditPerson();
            }
        });
    }

    private void initEditModalWindow() {
        try {
            loaderAddEdit.setLocation(getClass().getResource("/fxml/modalEditPersonWindow.fxml"));
            parentEdit = loaderAddEdit.load();
            editPersonController = loaderAddEdit.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDeleteModalWindow() {
        try {
            loaderDelete.setLocation(getClass().getResource("/fxml/modalDeletePersonWindow.fxml"));
            parentDelete = loaderDelete.load();
            deletePersonController = loaderDelete.getController();
            deletePersonController.setPersonList(personList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionButtonAddPerson() {
        editPersonController.setPerson(new Person());
        showDialog(ADD_TITLE, modalEditWindowStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
        if (!editPersonController.nameFieldIsEmpty(editPersonController.getPerson().getName())) {
            personList.addPerson(editPersonController.getPerson());
        }
    }

    @FXML
    private void actionButtonEditPerson() {
        editPersonController.setPerson(personTableView.getSelectionModel().getSelectedItem());
        showDialog(EDIT_TITLE, modalEditWindowStage, parentEdit, MODAL_WIDTH, MODAL_HEIGHT);
    }

    @FXML
    private void actionButtonDeletePerson() {
        deletePersonController.setPerson(personTableView.getSelectionModel().getSelectedItem());
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

    private void updatePersonListCount() {
        personListCount.setText(String.valueOf(personList.getPersonList().size()));
    }

    private void setActivityButtons() {
        if (personList.getPersonList().isEmpty()) disableButtons(true);
        else disableButtons(false);
    }

    private void disableButtons(boolean value) {
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    private void setFocus() {
        if (personList.getPersonList().size() == 1) personTableView.getSelectionModel().select(0);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public PersonListInterface getPersonList() {
        return personList;
    }

    public void setKeyWordsListController(KeyWordsListController keyWordsListController) {
        System.out.println(keyWordsListController);
        this.keyWordsListController = keyWordsListController;
    }
}