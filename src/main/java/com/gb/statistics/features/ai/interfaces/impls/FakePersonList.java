package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.model.Person;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class FakePersonList implements PersonListInterface {

    private ObservableList<Person> personList;

    public FakePersonList() {
        //Callback<Person, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        personList = FXCollections.observableArrayList();
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    @Override
    public void refreshPersonList() {

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