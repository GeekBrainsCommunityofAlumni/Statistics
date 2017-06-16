package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.interfaces.impls.FakeKeyWordsList;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class KeyWordsListController {

    private final String EMPTY_LIST_MESSAGE = "Список пуст";

    private PersonListInterface personList;
    private KeyWordsInterface keyWordsList = new FakeKeyWordsList();

    @FXML
    private ComboBox<Person> comboBoxPerson;

    @FXML
    private TableView<KeyWord> keyWordTableView;

    @FXML
    private TableColumn<KeyWord, String> columnKeyWordName;

    @FXML
    private void initialize() {
        columnKeyWordName.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
        keyWordTableView.setPlaceholder(new Label(EMPTY_LIST_MESSAGE));
        initListener();
        //keyWordTableView.setItems(keyWordsList.getKeyWordsByPerson(new Person()));
    }

    private void initComboBox() {
        comboBoxPerson.setItems(personList.getPersonList());
        comboBoxPerson.getSelectionModel().selectFirst();
    }

    private void initTableView(Person person) {
        System.out.println(person);
        keyWordTableView.setItems(keyWordsList.getKeyWordsByPerson(person));
    }

    private void initListener() {
        comboBoxPerson.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                System.out.println("Person: " + newValue.getName());
                initTableView(newValue);
            }
        });
    }

    private void setKeyWordsList() {

    }

    public void setKeyWordsList(KeyWordsInterface keyWordsList) {
        this.keyWordsList = keyWordsList;
    }

    public void setPersonList(PersonListInterface personList) {
        this.personList = personList;
        initComboBox();
    }
}
