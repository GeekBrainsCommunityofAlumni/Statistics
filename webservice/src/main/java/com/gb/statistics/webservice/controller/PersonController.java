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

        Person p = personRepository.addPerson(person);
        if(!(p==null)) return ResponseEntity.ok(p);

        return new ResponseEntity<Object>(new ErrorResponse("Bad request"),
                HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value="/person/{id}", method= RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@PathVariable("id") Integer id,
                                               @RequestBody Person person){
        Person p = personRepository.getPerson(id);
        if(p==null) return ResponseEntity.badRequest().body(
                new ErrorResponse("Bad Request"));
        p.setName(person.getName());
        p.setRank(person.getRank());

        //to delete later
        p.setName("Updated person");

        return ResponseEntity.ok(p);
    }

    //Это надо переписать
    @RequestMapping(value="/person/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable("id") Integer id){
        Person p = personRepository.getPerson(id);

        if(personRepository.delete(p))
            return ResponseEntity.ok().body(null);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Bad Request"));
    }
}
