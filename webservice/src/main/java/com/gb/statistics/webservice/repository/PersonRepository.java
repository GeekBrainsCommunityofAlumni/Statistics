package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;


public interface PersonRepository extends JpaRepository<Person,Long> {

    @Modifying
    Person update(Person p);
}
