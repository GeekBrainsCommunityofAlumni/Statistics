package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockSiteRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class SiteController {

    private MockSiteRepository mockSiteRepository = new MockSiteRepository();

    @RequestMapping(value = "/person/{id}", method = RequestMethod.GET)
    public Site getSite(@PathVariable("id") Integer id) {
        return mockSiteRepository.getById(id);
    }

    @RequestMapping(value = "/site", method = RequestMethod.POST)
    public ResponseEntity<?> addSite(@RequestBody Site site) {

        Site s = mockSiteRepository.add(site);
        if (!(s == null)) return ResponseEntity.ok(s);

        return new ResponseEntity<Object>(new ErrorResponse("Bad request"),
                HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/site/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteSite(@PathVariable("id") Integer id) {
        Site site = mockSiteRepository.getById(id);

        if (mockSiteRepository.delete(site))
            return ResponseEntity.ok().body(null);

        return ResponseEntity.badRequest()
                .body(new ErrorResponse("Bad Request"));
    }


}
