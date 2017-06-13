package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FakePersonList implements PersonListInterface {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public boolean addPerson(Person person) {
        personList.add(person);
        return true;
    }

    public boolean updatePerson(Person person) {
        return false;
    }

    public boolean deletePerson(Person person) {
        personList.remove(person);
        return true;
    }
}