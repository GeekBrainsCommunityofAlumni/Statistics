package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.interfaces.ModalControllerInterface;
import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.SiteListInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.model.Site;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DeleteWindowController implements ModalControllerInterface {

    private PersonListInterface personList;
    private Person person;
    private KeyWordsInterface keyWordList;
    private KeyWord keyWord;
    private SiteListInterface siteList;
    private Site site;
    private ListController currentController;

    @FXML
    private Label personNameLabel;

    public void setPerson(Person person, ListController controller) {
        this.person = person;
        this.currentController = controller;
        personNameLabel.setText(person.getName());
    }

    public void setKeyWord(KeyWord keyWord, ListController controller) {
        this.keyWord = keyWord;
        this.currentController = controller;
        personNameLabel.setText(keyWord.getName());
    }

    public void setSite(Site site, ListController controller) {
        this.site = site;
        this.currentController = controller;
        personNameLabel.setText(site.getName());
    }

    public void setPersonList(PersonListInterface personList) {
        this.personList = personList;
    }

    public void setSiteList(SiteListInterface siteList) {
        this.siteList = siteList;
    }

    public void setKeyWordList(KeyWordsInterface keyWordList) {
        this.keyWordList = keyWordList;
    }

    @FXML
    private void actionDelete(ActionEvent actionEvent) {
        System.out.println(currentController instanceof PersonListController);
        if (currentController instanceof PersonListController) personList.deletePerson(person);
        else if (currentController instanceof KeyWordsListController) keyWordList.deleteKeyWord(keyWord);
        else if (currentController instanceof SiteListController) siteList.deleteSite(site);
        actionClose(actionEvent);
    }

    @FXML
    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}