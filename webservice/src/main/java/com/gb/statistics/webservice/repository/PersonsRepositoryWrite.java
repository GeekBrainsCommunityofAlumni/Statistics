package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.model.Person;

public interface PersonsRepositoryWrite extends CrudRepository {

    void addNewPerson (Person person);
    void addNewKeywordsForPerson (Person person, String keyword);

}
