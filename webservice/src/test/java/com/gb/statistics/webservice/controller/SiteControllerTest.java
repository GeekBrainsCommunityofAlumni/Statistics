package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockSiteRepository;
import com.gb.statistics.webservice.repository.SiteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class SiteControllerTest {

    @Autowired
    SiteController siteController;

    @Autowired
    SiteRepository siteRepository;


    @Before
    public void startUp() {
        MockSiteRepository.siteList.add(new Site(1,"lenta.ru", "lenta.ru/index"));
        MockSiteRepository.siteList.add(new Site(2,"ria.ru", "ria.ru/index"));
    }

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

    @Test
    public void addSiteToMock() throws Exception {

        Site siteToAdd = new Site(3, "regnum.ru", "regnum.ru/news");
        siteController.addSite(siteToAdd);
        Assert.assertTrue(MockSiteRepository.siteList.contains(siteToAdd));
     }


}