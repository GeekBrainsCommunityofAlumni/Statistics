package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.Person;
import javafx.collections.ObservableList;

public interface PersonListInterface {

    ObservableList<Person> getPersonList();

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);



}
