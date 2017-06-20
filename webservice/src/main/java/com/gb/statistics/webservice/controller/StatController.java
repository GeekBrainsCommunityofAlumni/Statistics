package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.PersonRepository;
import com.gb.statistics.webservice.repository.SiteRepository;
import com.gb.statistics.webservice.repository.StatRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(path = "/api")
public class StatController {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private StatRepository statRepository;
    @Autowired
    private PersonRepository personRepository;


    //TODO if list.size==0 return not found

    @RequestMapping(path = "/stat/{siteId}")
    public ResponseEntity<?> getBySiteId(@PathVariable Integer siteId){
        Site s = siteRepository.findOne(siteId);
        if(s == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Site not found"));
        return ResponseEntity.ok(statRepository.findByPageSite(s));
    }

    @RequestMapping(path = "/stat/{siteId}/{personId}")
    public ResponseEntity<?> getBySiteIdPersonId(@PathVariable Integer siteId,
                                         @PathVariable Integer personId){
        Site s = siteRepository.findOne(siteId);
        Person p = personRepository.findOne(personId);
        if(s == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Site not found"));
        if(p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
        return ResponseEntity.ok(statRepository.findByPageSiteAndPerson(s, p));
    }

    @RequestMapping(path = "/stat/{siteId}/{personId}/{date}")
    public ResponseEntity<?> getBySiteIdPersonIdAndDate(@PathVariable Integer siteId,
                                         @PathVariable Integer personId,
                                         @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date){
        Site s = siteRepository.findOne(siteId);
        Person p = personRepository.findOne(personId);
        if(s == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Site not found"));
        if(p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
        return ResponseEntity.ok(statRepository.findByPageSiteAndPersonAndPageFounddatetime(s,p,date));
    }

    @RequestMapping(path = "/stat/{siteId}/{personId}/{date}/{dateTo}")
    public ResponseEntity<?> getBySiteIdPersonIdAndDate(@PathVariable Integer siteId,
                                                        @PathVariable Integer personId,
                                                        @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date,
                                                        @PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date dateTo){
        Site s = siteRepository.findOne(siteId);
        Person p = personRepository.findOne(personId);
        if(s == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Site not found"));
        if(p == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
        return ResponseEntity.ok(statRepository.findByPageSiteAndPersonAndPageFounddatetimeBetween(s,p,date,dateTo));
    }
}
