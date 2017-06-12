package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockSiteRepository;
import com.gb.statistics.webservice.repository.SiteRepository;
import com.gb.statistics.webservice.util.ErrorResponse;
import org.junit.Assert;
import org.junit.Before;
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

        ResponseEntity<List<Site>> responseEntity = siteController.getAllSites();
        List<Site> responseBody =  responseEntity.getBody();
        Assert.assertEquals(MockSiteRepository.siteList.size(), responseBody.size());
    }

    @Test
    public void addSiteToMock() throws Exception {

        Site siteToAdd = new Site(3, "regnum.ru", "regnum.ru/news");
        siteController.addSite(siteToAdd);
        Assert.assertTrue(MockSiteRepository.siteList.contains(siteToAdd));
     }

    @Test
    public void addSiteNullNameOrUrl() throws Exception {
        ResponseEntity responseEntityNameNull = siteController.addSite(new Site(4, null, "news.ru"));
        Assert.assertTrue(responseEntityNameNull.getBody() instanceof ErrorResponse);

        ResponseEntity responseEntityUrlNull = siteController.addSite(new Site(5, "site", null));
        Assert.assertTrue(responseEntityUrlNull.getBody() instanceof ErrorResponse);
    }

    @Test
    public void addSiteEquals() throws Exception {
        Site siteToAdd = new Site(10, "russiatoday.ru", "rt.ru");
        ResponseEntity responseEntity = siteController.addSite(siteToAdd);
        Assert.assertTrue(responseEntity.getBody().equals(siteToAdd));
    }

    @Test
    public void addSiteIsExist() throws Exception {
        ResponseEntity responseEntity = siteController.addSite(new Site(1, "lenta.ru", "lenta.ru/index"));
        Assert.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
    }

    @Test
    public void updateSiteIsNotExist() throws Exception {
        ResponseEntity responseEntity = siteController.updateSite(new Site(100500,"notFoundName", "notfoundsite.ru" ));
        Assert.assertTrue(responseEntity.getBody() instanceof ErrorResponse);
    }

    @Test
    public void updateSiteIsExistReturnOk() throws Exception {
        ResponseEntity responseEntity = siteController.updateSite(new Site(1,"lenta.ru", "lenta.ru/index"));
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void updateSite() throws Exception {
        List<Site> listCopy = MockSiteRepository.siteList;
        Site siteBeforeState = new Site();
        Site siteUpdateState = new Site(2, "changedSiteName", "changedUrl.com");

        for (Site site: listCopy) {
            if (site.getId() == siteUpdateState.getId()){
                siteBeforeState = MockSiteRepository.siteList.get(listCopy.indexOf(site));
            }
        }
        Site site = (Site) siteController.updateSite(siteUpdateState).getBody();

        Assert.assertNotEquals(siteBeforeState, site);
        Assert.assertEquals(siteUpdateState, site);
    }

}