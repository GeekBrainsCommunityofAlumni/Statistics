package com.gb.statistics.webservice.repository;

import com.gb.statistics.webservice.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer>{
}
