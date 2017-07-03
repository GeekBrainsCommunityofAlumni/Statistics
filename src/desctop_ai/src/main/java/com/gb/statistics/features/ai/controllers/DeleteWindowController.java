package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.ModalControllerInterface;
import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.model.Site;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteWindowController implements ModalControllerInterface {

    private ListInterface personList;
    private Person person;
    private ListInterface keyWordList;
    private KeyWord keyWord;
    private ListInterface siteList;
    private Site site;
    private ListController currentController;

    @FXML
    private Label nameLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    public void setPerson(Person person, ListController controller) {
        this.person = person;
        this.currentController = controller;
        nameLabel.setText(person.getName());
    }

    public void setKeyWord(KeyWord keyWord, ListController controller) {
        this.keyWord = keyWord;
        this.currentController = controller;
        nameLabel.setText(keyWord.getName());
    }

    public void setSite(Site site, ListController controller) {
        this.site = site;
        this.currentController = controller;
        nameLabel.setText(site.getName());
    }

    public void setPersonList(ListInterface personList) {
        this.personList = personList;
    }

    public void setSiteList(ListInterface siteList) {
        this.siteList = siteList;
    }

    public void setKeyWordList(ListInterface keyWordList) {
        this.keyWordList = keyWordList;
    }

    @FXML
    private void actionDelete(ActionEvent actionEvent) {
        setDisableButtons(true);
        if (currentController instanceof PersonListController) {
            personList.delete(person);
        }
        else if (currentController instanceof KeyWordsListController) {
            keyWordList.delete(keyWord);
        }
        else if (currentController instanceof SiteListController) {
            siteList.delete(site);
        }
        actionClose(actionEvent);
        setDisableButtons(false);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private void setDisableButtons(boolean value) {
        deleteButton.setDisable(value);
        cancelButton.setDisable(value);
    }
}