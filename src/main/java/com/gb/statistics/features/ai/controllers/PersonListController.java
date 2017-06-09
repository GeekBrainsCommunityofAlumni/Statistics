package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.impls.FakePersonList;
import com.gb.statistics.features.ai.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PersonListController {

    private FakePersonList fakePersonList = new FakePersonList();

    @FXML
    private TableView personList;

    @FXML
    private TableColumn<Person, String> columnPersonName;

    @FXML
    private void initialize() {
        columnPersonName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        personList.setItems(fakePersonList.getPersonList());
    }

    public void showDialog(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/addPersonFrame.fxml"));
            stage.setTitle("Добавить запись");
            stage.setMinWidth(300);
            stage.setMinHeight(150);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
