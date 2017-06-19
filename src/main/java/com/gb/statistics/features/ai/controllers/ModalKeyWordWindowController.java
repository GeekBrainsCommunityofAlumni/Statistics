package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.model.KeyWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ModalKeyWordWindowController {

    private KeyWord keyWord;

    @FXML
    private TextField keyWordNameField;

    @FXML
    private Label errorLabel;

    public void setKeyWord(KeyWord keyWord) {
        this.keyWord = keyWord;
        keyWordNameField.setText(keyWord.getName());
    }

    @FXML
    private void actionSave(ActionEvent actionEvent) {
        if (nameFieldIsEmpty(keyWordNameField.getText())) {
            setVisibleErrorLabel(true);
            return;
        }
        keyWord.setName(keyWordNameField.getText());
        actionClose(actionEvent);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        setVisibleErrorLabel(false);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public KeyWord getKeyWord() {
        return keyWord;
    }

    public boolean nameFieldIsEmpty(String fieldValue) {
        return fieldValue.trim().equals("");
    }

    private void setVisibleErrorLabel(boolean value) {
        errorLabel.setVisible(value);
    }
}