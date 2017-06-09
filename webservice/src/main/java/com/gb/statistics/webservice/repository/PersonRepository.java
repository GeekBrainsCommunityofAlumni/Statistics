package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Person;

import java.util.List;

public interface PersonRepository {

    Person get(Integer id);
    List<Person> getAll();
    Person getByName(String name);

    Person update(Person p);

    Person add(Person person);
    boolean isExists(Person person);

    boolean delete(Person p);
}
