package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.impls.FakePersonList;
import com.gb.statistics.features.ai.model.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModalPersonWindowController {

    private FakePersonList personList;
    private Person person;

    @FXML
    private TextField personNameField;

    public void setPerson(Person person) {
        this.person = person;
        personNameField.setText(person.getName());
        System.out.println(this.person);
    }

    @FXML
    private void actionSave(ActionEvent actionEvent) {
        System.out.println(person);
        person.setName(personNameField.getText());
        personList.addPerson(person);
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

    public void setPersonList(FakePersonList personList) {
        this.personList = personList;
    }
}