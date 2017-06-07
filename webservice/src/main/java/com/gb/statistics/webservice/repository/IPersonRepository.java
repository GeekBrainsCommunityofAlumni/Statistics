package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entitys.Person;

import java.util.List;

public interface IPersonRepository {

    Person getPerson(Integer id);
    List<Person> getAllPersons();
}
