package com.gb.statistics.features.ai.controllers;

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
    private final int MODAL_EDIT_HEIGHT = 150;
    private final int MODAL_DELETE_HEIGHT = 100;
    private final String ADD_TITLE = "Создание записи";
    private final String EDIT_TITLE = "Редактирование записи";
    private final String DELETE_TITLE = "Удаление записи";
    private final String EMPTY_LIST = "Список пуст";

    private FakePersonList fakePersonList = new FakePersonList();
    private Parent parentAdd;
    private Parent parentEdit;
    private Parent parentDelete;
    private FXMLLoader loaderAdd = new FXMLLoader();
    private FXMLLoader loaderEdit = new FXMLLoader();
    private FXMLLoader loaderDelete = new FXMLLoader();
    private ModalPersonWindowController modalPersonWindowController;
    private DeletePersonController deletePersonController;
    private Stage modalAddWindowStage;
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
    private ModalPersonWindowController addPersonController;

    @FXML
    private void initialize() {
        columnPersonName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        personTableView.setPlaceholder(new Label(EMPTY_LIST));
        personTableView.setItems(fakePersonList.getPersonList());
        initListeners();
        initAddModalWindow();
        initEditModalWindow();
        initDeleteModalWindow();
        fakePersonList.readPersonList();
    }

    private void initListeners() {
        fakePersonList.getPersonList().addListener((ListChangeListener<Person>) c -> {
            updatePersonListCount();
            setActivityButtons();
            setFocus();
        });

        personTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == CLICK_COUNT && !fakePersonList.getPersonList().isEmpty()) {
                actionButtonEditPerson();
            }
        });
    }

    private void initAddModalWindow() {
        try {
            loaderAdd.setLocation(getClass().getResource("/fxml/modalPersonWindow.fxml"));
            parentAdd = loaderAdd.load();
            addPersonController = loaderAdd.getController();
            addPersonController.setPersonList(fakePersonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initEditModalWindow() {
        try {
            loaderEdit.setLocation(getClass().getResource("/fxml/modalPersonWindow.fxml"));
            parentEdit = loaderEdit.load();
            modalPersonWindowController = loaderEdit.getController();
            modalPersonWindowController.setPersonList(fakePersonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDeleteModalWindow() {
        try {
            loaderDelete.setLocation(getClass().getResource("/fxml/modalDeletePersonWindow.fxml"));
            parentDelete = loaderDelete.load();
            deletePersonController = loaderDelete.getController();
            deletePersonController.setPersonList(fakePersonList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void actionButtonDeletePerson() {
        deletePersonController.setPerson(personTableView.getSelectionModel().getSelectedItem());
        showDialog(DELETE_TITLE, modalDeleteWindowStage, parentDelete, MODAL_WIDTH, MODAL_DELETE_HEIGHT);
    }

    @FXML
    private void actionButtonEditPerson() {
        modalPersonWindowController.setPerson(personTableView.getSelectionModel().getSelectedItem());
        showDialog(EDIT_TITLE, modalEditWindowStage, parentEdit, MODAL_WIDTH, MODAL_EDIT_HEIGHT);
    }

    @FXML
    private void actionButtonAddPerson() {
        modalPersonWindowController.setPerson(new Person());
        System.out.println(modalPersonWindowController.getPerson());
        showDialog(ADD_TITLE, modalAddWindowStage, parentAdd, MODAL_WIDTH, MODAL_EDIT_HEIGHT);
    }

    private void showDialog(String title, Stage stage, Parent parent, int width, int height) {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle(title);
            stage.setMinWidth(width);
            stage.setMinHeight(height);
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mainStage);

            switch (title) {
                case ADD_TITLE:
                    setModalAddWindowStage(stage);
                    break;
                case EDIT_TITLE:
                    setModalEditWindowStage(stage);
                    break;
                case DELETE_TITLE:
                    setModalDeleteWindowStage(stage);
                    break;
            }
        }
        stage.showAndWait();
    }

    private void updatePersonListCount() {
        personListCount.setText(String.valueOf(fakePersonList.getPersonList().size()));
    }

    private void setActivityButtons() {
        if (fakePersonList.getPersonList().isEmpty()) disableButtons(true);
        else disableButtons(false);
    }

    private void disableButtons(boolean value) {
        editButton.setDisable(value);
        deleteButton.setDisable(value);
    }

    private void setFocus() {
        if (fakePersonList.getPersonList().size() == 1) personTableView.getSelectionModel().select(0);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setModalDeleteWindowStage(Stage modalDeleteWindowStage) {
        this.modalDeleteWindowStage = modalDeleteWindowStage;
    }

    public void setModalEditWindowStage(Stage modalEditWindowStage) {
        this.modalEditWindowStage = modalEditWindowStage;
    }

    public void setModalAddWindowStage(Stage modalAddWindowStage) {
        this.modalAddWindowStage = modalAddWindowStage;
    }
}