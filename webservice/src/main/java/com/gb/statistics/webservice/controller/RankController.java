package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.entity.PersonPageRank;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.RankRepository;
import com.gb.statistics.webservice.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Kogut Sergey on 18.06.17.
 */
@RestController
public class RankController {
    @Autowired
    RankRepository rankRepository;

    @RequestMapping(value="/site", method = RequestMethod.GET)
    public ResponseEntity<List<PersonPageRank>> getAllSites(){
        return ResponseEntity.ok(rankRepository.findAll());
    }
}

