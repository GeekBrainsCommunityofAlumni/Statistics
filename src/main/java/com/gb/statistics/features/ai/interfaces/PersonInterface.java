package com.gb.statistics.features.ai.interfaces;

import com.gb.statistics.features.ai.model.Person;
import java.util.List;

public interface PersonInterface {

    List<Person> readPersonList();

    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(Person person);

}
