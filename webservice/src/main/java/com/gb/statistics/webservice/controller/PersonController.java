package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value="/person/{id}", method= RequestMethod.GET)
    public ResponseEntity<Person> getPerson(@PathVariable("id") Integer id){
        return ResponseEntity.ok(personRepository.getPerson(id));
    }

    @RequestMapping(value="/person", method= RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok(personRepository.getAllPersons());
    }


    /*
    * Как вариант возвращать собственные меседжи в виде джисона
    * с описаниея ошибок например, ща каркас накидаю, если не приживется удалим
    * */
    @RequestMapping(value="/person", method= RequestMethod.POST)
    public ResponseEntity<?> addPerson(@RequestBody Person person){
        if(personRepository.addPerson(person))
            //Если все прошло удачно то, наверно, можно вернуть свежедобавленный Person
            return ResponseEntity.accepted().body(null);
        return ResponseEntity.badRequest().body(null);
        //return ResponseEntity.badRequest().body(new ErrorResponse(message));
    }

}
