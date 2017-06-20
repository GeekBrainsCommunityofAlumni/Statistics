package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockSiteRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

import static com.gb.statistics.webservice.controller.InitTestModel.*;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class SiteControllerTest {

    @Autowired
    private SiteController siteController;

    @Before
    public void startUp() {
        init();
    }

    @Test
    public void getAllSitesNotNull() throws Exception {
        Assert.assertNotNull(siteController.getAllSites());
    }

    @Test
    public void getAllSitesSizeTest() throws Exception {
        ResponseEntity<List<Site>> responseEntity = siteController.getAllSites();
        List<Site> responseBody = responseEntity.getBody();
        Assert.assertEquals(MockSiteRepository.siteList.size(), responseBody.size());

    }

    @Test
    public void addSiteToMock() throws Exception {
        Site siteToAdd = new Site(NOT_EXISTING_ID,
                NOT_EXISTING_SITE_NAME,
                NOT_EXISTING_SITE_URL);
        siteController.addSite(siteToAdd);
        Assert.assertTrue(MockSiteRepository.siteList.contains(siteToAdd));
    }

    @Test
    public void addSiteNullNameOrUrl() throws Exception {
        ResponseEntity responseEntityNameNull = siteController.addSite(new Site(NOT_EXISTING_ID, null, NOT_EXISTING_SITE_URL));
        Assert.assertTrue(responseEntityNameNull.getBody() instanceof ErrorResponse);

        ResponseEntity responseEntityUrlNull = siteController.addSite(new Site(NOT_EXISTING_ID, NOT_EXISTING_SITE_NAME, null));
        Assert.assertTrue(responseEntityUrlNull.getBody() instanceof ErrorResponse);
    }

    @Test
    public void addSiteEquals() throws Exception {
        Site siteToAdd = new Site(NOT_EXISTING_ID, NOT_EXISTING_SITE_NAME, NOT_EXISTING_SITE_URL);
        ResponseEntity responseEntity = siteController.addSite(siteToAdd);
        Assert.assertTrue(responseEntity.getBody().equals(siteToAdd));
    }

    @Test
    public void addSiteIsExist() throws Exception {
        ResponseEntity responseEntity = siteController.addSite(new Site(EXISTING_ID, EXISTING_SITE_NAME, EXISTING_SITE_URL));
        Assert.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
    }

    @Test
    public void updateSiteIsNotExist() throws Exception {
        ResponseEntity responseEntity = siteController.updateSite(new Site(NOT_EXISTING_ID, NOT_EXISTING_SITE_NAME, NOT_EXISTING_SITE_URL));
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }

    @Ignore  // MockSiteRepository isExist() strange
    @Test
    public void updateSiteIsExistReturnOk() throws Exception {
        ResponseEntity responseEntity = siteController.updateSite
                (new Site(EXISTING_ID, UPDATED_SITE_NAME, UPDATED_SITE_URL));
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }


    @Test
    public void deleteSiteNotContainsInMock() throws Exception {
        Site site = MockSiteRepository.siteList.get(DELETE_ID);
        ResponseEntity responseEntity = siteController.deleteSite(site.getId());
        Assert.assertFalse(MockSiteRepository.siteList.contains(site));
       Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void deleteSiteNotFound() throws Exception {
        Assert.assertTrue(siteController.deleteSite(NOT_EXISTING_ID)
                .getStatusCode()
                .is4xxClientError());
    }

}