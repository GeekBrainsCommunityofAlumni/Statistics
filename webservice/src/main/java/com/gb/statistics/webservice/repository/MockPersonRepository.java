package com.gb.statistics.webservice.repository;

import com.gb.statistics.webservice.entity.Person;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class MockPersonRepository implements PersonRepository {

    private static int count;
    private static List<Person> personsList = new LinkedList<Person>();

    @Override
    public Person get(Integer id) {
        for (Person p : personsList) {
            if (p.getId()== id) return p;
        }
        return null;
    }

    @Override
    public List<Person> getAll() {
        return personsList;
    }

    @Override
    public Person add(Person person) {
        person.setId(++count);
        personsList.add(person);
        return person;
    }

    @Override
    public boolean isExists(Person person) {
        return personsList.contains(person);
    }

    @Override
    public Person getByName(String name) {
        return null;
    }

    @Override
    public Person update(Person person) {
        for (Person p : personsList) {
            if (p.getId()==person.getId()){
                p.setName(person.getName());
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean delete(Person p) {
        if(personsList.contains(p)){
            personsList.remove(p);
            return true;
        }
        return false;
    }
}
