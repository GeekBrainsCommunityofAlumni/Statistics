package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.PersonRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(path="/person", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(personRepository.findAll());
    }

    @RequestMapping(path = "/person/{id}")
    public ResponseEntity<?> getByPersonId(@PathVariable Integer id){
        if (!personRepository.exists(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        return ResponseEntity.ok(personRepository.findOne(id));
    }

    @RequestMapping(path = "/person", method = RequestMethod.POST)
    public ResponseEntity<?> addPerson(@RequestBody Person person){
        String name = person.getName();
        if (name == null || name.equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Name must not be null or empty"));
        return ResponseEntity.ok(personRepository.save(person));
    }

    @RequestMapping(path = "/person", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@RequestBody Person person){
        Person p = personRepository.findOne(person.getId());
        if (p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        String name = person.getName();
        if (name == null || name.equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Name must be not null or empty"));

        p.setName(name);
        return ResponseEntity.ok(personRepository.save(p));
    }

    @RequestMapping(path = "/person/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable Integer id){
        if (!personRepository.exists(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        personRepository.delete(id);
        return ResponseEntity.ok(null);
    }

}
