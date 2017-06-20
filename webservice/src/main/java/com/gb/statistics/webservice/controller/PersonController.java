package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.PersonRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(value="/person/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> getPerson(@PathVariable("id") Integer id){
        Person p = personRepository.get(id);
        if(p==null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        return ResponseEntity.ok(p);
    }

    @RequestMapping(value="/person", method= RequestMethod.GET)
    public ResponseEntity<List<Person>> getAllPersons(){
        return ResponseEntity.ok(personRepository.getAll());
    }


    @RequestMapping(value="/person", method= RequestMethod.POST)
    public ResponseEntity<?> addPerson(@RequestBody Person person){

        if(!personRepository.isExists(person)){
            Person p = personRepository.add(person);
            return ResponseEntity.ok(p);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("Person already exists"));
    }


    @RequestMapping(value="/person", method= RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@RequestBody Person person){

        if(person==null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        Person p = personRepository.update(person);

        return ResponseEntity.ok(p);
    }

    @RequestMapping(value="/person/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable("id") Integer id){
        Person p = personRepository.get(id);

        if(personRepository.delete(p))
            return ResponseEntity.ok().body(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity badRequest(){
        return ResponseEntity.badRequest().body(new ErrorResponse("Bad Request"));
    }
}
