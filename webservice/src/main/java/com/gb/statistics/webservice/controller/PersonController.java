package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/person/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") Integer id){

        return ResponseEntity.ok(personRepository.getPerson(id));
    }

    @RequestMapping("/person")
    public List<Person> getAllPersons(){
        return personRepository.getAllPersons();
    }

}
