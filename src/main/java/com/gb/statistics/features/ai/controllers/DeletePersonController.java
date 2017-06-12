package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.impls.FakePersonList;
import com.gb.statistics.features.ai.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeletePersonController {

    private FakePersonList personList;
    private Person person;

    @FXML
    private Label personNameLabel;

    public void setPerson(Person person) {
        this.person = person;
        personNameLabel.setText(person.getName());
    }

    public void setPersonList(FakePersonList personList) {
        this.personList = personList;
    }

    @FXML
    private void actionDelete(ActionEvent actionEvent) {
        personList.deletePerson(person);
        actionClose(actionEvent);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
