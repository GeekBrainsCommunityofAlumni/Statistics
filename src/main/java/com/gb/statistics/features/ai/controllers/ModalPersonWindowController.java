package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModalPersonWindowController {

    private Person person;

    @FXML
    private TextField personNameField;

    public void setPerson(Person person) {
        this.person = person;
        personNameField.setText(person.getName());
    }

    @FXML
    private void actionSave(ActionEvent actionEvent) {
        person.setName(personNameField.getText());
        actionClose(actionEvent);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public Person getPerson() {
        return person;
    }
}