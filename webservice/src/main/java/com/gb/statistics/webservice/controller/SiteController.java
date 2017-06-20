package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.SiteRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SiteController {

    @Autowired
    SiteRepository siteRepository;

    @RequestMapping(value="/site", method = RequestMethod.GET)
    public ResponseEntity<List<Site>> getAllSites(){
        return ResponseEntity.ok(siteRepository.findAll());
    }

    @RequestMapping(value="/site", method = RequestMethod.POST)
    public ResponseEntity<?> addSite(@RequestBody Site site){
        if(site.getName()==null||site.getUrl()==null)
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("Name and URL not be null!"));

        if (siteRepository.exists(site.getId()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Site already exists!"));

        Site s = siteRepository.save(site);
        return ResponseEntity.ok(s);
    }

    @RequestMapping(value="/site", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSite(@RequestBody Site site){
        if (!siteRepository.exists(site.getId()))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Site not exists!"));

        Site s = siteRepository.update(site);
        return ResponseEntity.ok(s);
    }

    @RequestMapping(value="/site/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSite(@PathVariable Long id){
        Site s = siteRepository.getOne(id);
        siteRepository.delete(s);
        if(siteRepository.exists(s.getId()))
            return ResponseEntity.ok().body(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity badRequest(){
        return ResponseEntity.badRequest().body(new ErrorResponse("Bad Request"));
    }
}
