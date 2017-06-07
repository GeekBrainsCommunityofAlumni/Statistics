package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.Person;
import java.util.ArrayList;

public interface PersonInterface {

    ArrayList<Person> readPersonList();

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

}
