package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Keyword;
import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.KeywordRepository;
import com.gb.statistics.webservice.repository.PersonRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KeywordController {


    @Autowired
    private KeywordRepository keywordRepository;
    @Autowired
    private PersonRepository personRepository;

    @RequestMapping(path = "/keyword", method = RequestMethod.GET)
    public ResponseEntity<?> getAllKeywords(){
        return ResponseEntity.ok(keywordRepository.findAll());
    }

    @RequestMapping(path = "/keyword/{personId}", method = RequestMethod.GET)
    public ResponseEntity<?> getByPersonId(@PathVariable Integer personId){
        List<Keyword> k = keywordRepository.findByPersonId(personId);
        if(k.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Keywords not found"));
        return ResponseEntity.ok(k);
    }

    @RequestMapping(path = "/keyword/{personId}", method = RequestMethod.POST)
    public ResponseEntity<?> addKeyword(@PathVariable Integer personId, @RequestBody Keyword keyword){
        Person p = personRepository.findOne(personId);
        if (keyword.getName().equals("")) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("Name must not be empty!"));
        if (p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
        keyword.setPerson(p);

        return ResponseEntity.ok(keywordRepository.save(keyword));
    }

    @RequestMapping(path = "/keyword", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePerson(@RequestBody Keyword keyword){

        if(!keywordRepository.exists(keyword.getId())) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Keyword not found"));
        if (keyword.getName()==null||keyword.getName().equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Name must not be empty!"));

        Keyword k = keywordRepository.findOne(keyword.getId());
        k.setName(keyword.getName());
        return ResponseEntity.ok(keywordRepository.save(k));
    }

    @RequestMapping(path = "/keyword/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePerson(@PathVariable Integer id){
        if(!keywordRepository.exists(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Keyword not found"));
        keywordRepository.delete(id);
        return ResponseEntity.ok(null);
    }
}
