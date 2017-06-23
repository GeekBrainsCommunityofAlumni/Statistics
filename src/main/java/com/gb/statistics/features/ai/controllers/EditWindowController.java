package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ModalControllerInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.model.Site;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditWindowController implements ModalControllerInterface {

    private Person person;
    private ModelListData keyWord = new KeyWord();
    private Site site;
    private ListController currentController;

    @FXML
    private TextField personNameField;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        personNameField.requestFocus();
    }

    public void setPerson(Person person, ListController controller) {
        this.person = person;
        this.currentController = controller;
        personNameField.setText(person.getName());
    }

    public void setKeyWord(KeyWord keyWord, Person person, ListController controller) {
        this.keyWord = keyWord;
        this.person = person;
        this.currentController = controller;
        personNameField.setText(keyWord.getName());
    }

    public void setSite(Site site, ListController controller) {
        this.site = site;
        this.currentController = controller;
        personNameField.setText(site.getName());
    }

    @FXML
    private void actionSave(ActionEvent actionEvent) {
        if (nameFieldIsEmpty(personNameField.getText())) {
            setVisibleErrorLabel(true);
            return;
        }
        if (currentController instanceof PersonListController) {
            person.setName(personNameField.getText());
        } else if (currentController instanceof KeyWordsListController) {
            keyWord.setName(personNameField.getText());
            ((KeyWord)keyWord).setPersonId(person.getId());
        } else if (currentController instanceof SiteListController) {
            site.setName(personNameField.getText());
        }
        actionClose(actionEvent);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        setVisibleErrorLabel(false);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public Person getPerson() {
        return person;
    }

    public boolean nameFieldIsEmpty(String fieldValue) {
        return fieldValue.trim().equals("");
    }

    private void setVisibleErrorLabel(boolean value) {
        errorLabel.setVisible(value);
    }

    public KeyWord getKeyWord() {
        return (KeyWord) keyWord;
    }

    public Site getSite() {
        return site;
    }
}