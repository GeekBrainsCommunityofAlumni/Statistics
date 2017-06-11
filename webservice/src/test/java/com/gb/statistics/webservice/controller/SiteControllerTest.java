package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.SiteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class SiteControllerTest {

    @Autowired
    SiteController siteController;

    @Autowired
    SiteRepository siteRepository;



    @Test
    public void getAllSitesNotNull() throws Exception {
        Assert.assertNotNull(siteController.getAllSites());
    }

    @Test
    public void getAllSitesSizeTest() throws Exception {

        List<Site> allSites = siteRepository.getAll();
        ResponseEntity<List<Site>> responseEntity = siteController.getAllSites();
        List<Site> responseBody =  responseEntity.getBody();
        Assert.assertEquals(allSites.size(), responseBody.size());
    }





}