package com.gb.statistics.features.ai.controllers;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.interfaces.impls.FakeKeyWordsList;
import com.gb.statistics.features.ai.model.Person;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.stream.Collectors;

public class KeyWordsListController {

    private KeyWordsInterface keyWordsList = new FakeKeyWordsList();
    private ObservableList<Person> personList;

    @FXML
    private ComboBox<Person> comboBoxPerson;

    @FXML
    private void initialize() {

        //choiceBoxPerson.setCellValueFactory(cellData -> cellData.getValue().getNameProperty());
    }


    public void setKeyWordsList(KeyWordsInterface keyWordsList) {
        this.keyWordsList = keyWordsList;
    }

    public void setPersonList(ObservableList<Person> personList) {
        System.out.println(personList);
        this.personList = personList;
        System.out.println(personList);

        comboBoxPerson.setItems(personList);
        comboBoxPerson.getSelectionModel().selectFirst();

        comboBoxPerson.setCellFactory(new Callback<ListView<Person>, ListCell<Person>>() {
            @Override
            public ListCell<Person> call(ListView<Person> param) {

                return new ListCell<Person>(){
                    @Override
                    public void updateItem(Person item, boolean empty){
                        super.updateItem(item, empty);
                        if(!empty) {
                            setText(item.getName());
                            setGraphic(null);
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });

        comboBoxPerson.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {

            @Override
            public void changed(ObservableValue<? extends Person> observable,
                                Person oldValue, Person newValue) {
                System.out.println("Person: " + newValue.getName());

            }
        });

        System.out.println(comboBoxPerson.getSelectionModel().getSelectedItem());
    }
}
