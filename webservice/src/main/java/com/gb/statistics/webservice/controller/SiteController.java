package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.SiteRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SiteController {

   private SiteRepository siteRepository;

    @RequestMapping(value="/site/{id}", method= RequestMethod.GET)
    public ResponseEntity<Site> getSite (@PathVariable ("id") Integer id) {
        return ResponseEntity.ok(siteRepository.getById(id));
    }

    @RequestMapping(value="/site", method= RequestMethod.GET)
    public ResponseEntity<List<Site>> getAllSites(){
        return ResponseEntity.ok(siteRepository.getAll());
    }

    @RequestMapping(value="/site", method= RequestMethod.POST)
    public ResponseEntity<?> addSite (@RequestBody Site site){

        Site s = siteRepository.add(site);
        if(!(s==null)) return ResponseEntity.ok(s);

        return new ResponseEntity<Object>(new ErrorResponse("Bad request"),
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value="/site/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> deleteSite (@PathVariable("id") Integer id){
        Site site = siteRepository.getById(id);

        if(siteRepository.delete(site))
            return ResponseEntity.ok().body(null);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Bad Request"));
    }


}
