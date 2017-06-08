package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.impls.FakePersonList;
import com.gb.statistics.features.ai.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
}
