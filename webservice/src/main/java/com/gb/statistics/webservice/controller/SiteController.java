package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.SiteRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class SiteController {

    @Autowired
    private SiteRepository siteRepository;

    @RequestMapping(path = "/site", method = RequestMethod.GET)
    public ResponseEntity<?> getAllSites(){
        return ResponseEntity.ok(siteRepository.findAll());
    }

    @RequestMapping(path = "/site/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBySiteId(@PathVariable Integer id){
        if(!siteRepository.exists(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));
        return ResponseEntity.ok(siteRepository.findOne(id));
    }

    @RequestMapping(path = "/site", method = RequestMethod.POST)
    public ResponseEntity<?> addSite(@RequestBody Site site){
        String name = site.getName();
        if (name == null || name.equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Name must be not null or empty"));
        return ResponseEntity.ok(siteRepository.save(site));
    }

    @RequestMapping(path = "/site", method = RequestMethod.PUT)
    public ResponseEntity<?> updateSite(@RequestBody Site site){
        Site s = siteRepository.findOne(site.getId());
        if (s == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        String name = site.getName();
        if (name == null || name.equals(""))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Name must be not null or empty"));

        s.setName(name);
        return ResponseEntity.ok(siteRepository.save(s));
    }

    @RequestMapping(path = "/site/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSite(@PathVariable Integer id){
        if (!siteRepository.exists(id)) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Person not found"));

        siteRepository.delete(id);
        return ResponseEntity.ok(null);
    }

}
