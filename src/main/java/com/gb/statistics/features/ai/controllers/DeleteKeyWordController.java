package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteKeyWordController {

    private KeyWordsInterface keyWordList;
    private KeyWord keyWord;

    @FXML
    private Label keyWordNameLabel;

    public void setKeyWord(KeyWord keyWord) {
        this.keyWord = keyWord;
        keyWordNameLabel.setText(keyWord.getName());
    }

    public void setKeyWordList(KeyWordsInterface keyWordList) {
        this.keyWordList = keyWordList;
    }

    @FXML
    private void actionDelete(ActionEvent actionEvent) {
        keyWordList.deleteKeyWord(keyWord);
        actionClose(actionEvent);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
