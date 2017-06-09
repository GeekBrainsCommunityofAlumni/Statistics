package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Keyword;
import com.gb.statistics.webservice.repository.KeywordRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class KeywordController {

    @Autowired
    KeywordRepository keywordRepository;

    @RequestMapping(value = "/keyword", method = RequestMethod.GET)
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(keywordRepository.getAll());
    }

    @RequestMapping(value = "/keyword/{personId}", method = RequestMethod.GET)
    public ResponseEntity<?> getByPerson(@PathVariable Integer personId){
        return ResponseEntity.ok(keywordRepository.getByPerson(personId));
    }

    @RequestMapping(value = "/keyword", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Keyword keyword){
        if(keyword.getPersonId()==0||keyword.getName()==null){
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("`personId` and `name` not be null!"));
        }
        if (keywordRepository.isExists(keyword))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Keyword already exists!"));

        Keyword k = keywordRepository.add(keyword);
        return ResponseEntity.ok(k);
    }

    @RequestMapping(value = "/keyword", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Keyword keyword){
        if(!keywordRepository.isExists(keyword)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Keyword not exists!"));
        }
        Keyword k = keywordRepository.update(keyword);
        return ResponseEntity.ok(k);
    }

    @RequestMapping(value = "/keyword/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Keyword k = keywordRepository.get(id);

        if(keywordRepository.delete(k))
            return ResponseEntity.ok().body(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity badRequest(){
        return ResponseEntity.badRequest().body(new ErrorResponse("Bad Request"));
    }
}
