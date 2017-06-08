package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Person;

import java.util.List;

public interface PersonRepository {

    Person getPerson(Integer id);
    List<Person> getAllPersons();

    Person addPerson(Person person);
    boolean isExists(int id);

    boolean delete(Person p);
}
