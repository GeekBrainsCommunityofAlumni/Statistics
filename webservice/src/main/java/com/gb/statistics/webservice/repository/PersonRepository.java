package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Person;

import java.util.List;

public interface PersonRepository extends IRepository<Person>{


    boolean isExists(int id);


}
