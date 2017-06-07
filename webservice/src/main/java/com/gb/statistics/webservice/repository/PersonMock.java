package com.gb.statistics.webservice.repository;

import com.gb.statistics.webservice.entitys.Person;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class PersonMock implements IPersonRepository {

    @Override
    public Person getPerson(Integer id) {
        return new Person(id, "Test");
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> p = new LinkedList<Person>();
        p.add(new Person(1, "Putin"));
        p.add(new Person(2, "Medved"));
        p.add(new Person(3, "Nava"));
        return p;
    }
}
