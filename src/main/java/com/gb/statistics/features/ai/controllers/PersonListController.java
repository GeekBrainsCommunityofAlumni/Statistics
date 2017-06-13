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

    private FakePersonList fakePersonList = new FakePersonList();
    private Parent parentEdit;
    private Parent parentDelete;
    private FXMLLoader loaderEdit = new FXMLLoader();
    private FXMLLoader loaderDelete = new FXMLLoader();
    private ModalPersonWindowController modalPersonWindowController;
    private DeletePersonController deletePersonController;
    private Stage modalWindowStage;
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
        personTableView.setPlaceholder(new Label("Список пуст"));
        initListeners();
        setDisableButtons();
        fakePersonList.readPersonList();
        personTableView.setItems(fakePersonList.getPersonList());
        personTableView.getSelectionModel().select(0);
        initEditModalWindow();
        initDeleteModalWindow();
    }

    private void initListeners() {
        fakePersonList.getPersonList().addListener((ListChangeListener<Person>) c -> {
            updatePersonListCount();
            setDisableButtons();
        });

        personTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !fakePersonList.getPersonList().isEmpty()) {
                actionButtonEditPerson();
            }
        });
    }

    private void initEditModalWindow() {
        try {
            loaderEdit.setLocation(getClass().getResource("/fxml/modalPersonWindow.fxml"));
            parentEdit = loaderEdit.load();
            modalPersonWindowController = loaderEdit.getController();
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
        showDeleteDialog();
    }

    @FXML
    private void actionButtonEditPerson() {
        modalPersonWindowController.setPerson(personTableView.getSelectionModel().getSelectedItem());
        showEditDialog();
    }

    @FXML
    private void actionButtonAddPerson() {
        modalPersonWindowController.setPerson(new Person());
        showEditDialog();
        fakePersonList.addPerson(modalPersonWindowController.getPerson());
    }

    private void showEditDialog() {
        if (modalWindowStage == null) {
            modalWindowStage = new Stage();
            modalWindowStage.setTitle("Добавление записи");
            modalWindowStage.setMinWidth(300);
            modalWindowStage.setMinHeight(150);
            modalWindowStage.setResizable(false);
            modalWindowStage.setScene(new Scene(parentEdit));
            modalWindowStage.initModality(Modality.WINDOW_MODAL);
            modalWindowStage.initOwner(mainStage);
        }
        modalWindowStage.showAndWait();
    }

    private void showDeleteDialog() {
        if (modalDeleteWindowStage == null) {
            modalDeleteWindowStage = new Stage();
            modalDeleteWindowStage.setTitle("Удаление записи");
            modalDeleteWindowStage.setMinWidth(300);
            modalDeleteWindowStage.setMinHeight(100);
            modalDeleteWindowStage.setResizable(false);
            modalDeleteWindowStage.setScene(new Scene(parentDelete));
            modalDeleteWindowStage.initModality(Modality.WINDOW_MODAL);
            modalDeleteWindowStage.initOwner(mainStage);
        }
        modalDeleteWindowStage.showAndWait();
    }

    private void updatePersonListCount() {
        personListCount.setText(String.valueOf(fakePersonList.getPersonList().size()));
    }

    private void setDisableButtons() {
        if (fakePersonList.getPersonList().isEmpty()) {
            editButton.setDisable(true);
            deleteButton.setDisable(true);
        } else {
            editButton.setDisable(false);
            deleteButton.setDisable(false);
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}