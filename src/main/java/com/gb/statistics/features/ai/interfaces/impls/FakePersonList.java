package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.PersonInterface;
import com.gb.statistics.features.ai.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class FakePersonList implements PersonInterface {

    private ObservableList<Person> personList = FXCollections.observableList(readPersonList());

    public List<Person> readPersonList() {
        return addFakeData();
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

    public List<Person> addFakeData() {
        List<Person> fakePersonList = new ArrayList<Person>();
        fakePersonList.add(new Person(1, "Путин"));
        fakePersonList.add(new Person(2, "Медведев"));
        fakePersonList.add(new Person(3, "Навальный"));
        return fakePersonList;
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }
}
