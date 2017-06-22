package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.Person;
import javafx.collections.ObservableList;

public interface PersonListInterface {

    void refreshPersonList();

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

    ObservableList<Person> getSiteList();
}
