package com.gb.statistics.webservice.controllers;

import com.gb.statistics.webservice.entitys.Person;
import com.gb.statistics.webservice.repository.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private IPersonRepository personRepository;

    @RequestMapping("/person/{id}")
    public Person getPerson(@PathVariable("id") Integer id){
        return personRepository.getPerson(id);
    }

    @RequestMapping("/person")
    public List<Person> getAllPersons(){
        return personRepository.getAllPersons();
    }

}
