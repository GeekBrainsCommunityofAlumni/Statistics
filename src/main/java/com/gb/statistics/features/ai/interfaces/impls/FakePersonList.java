package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.PersonInterface;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class FakePersonList implements PersonInterface {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public List<Person> readPersonList() {
        addFakeData();
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

    public void addFakeData() {
        personList.add(new Person(1, "Путин"));
        personList.add(new Person(2, "Медведев"));
        personList.add(new Person(3, "Навальный"));
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }
}