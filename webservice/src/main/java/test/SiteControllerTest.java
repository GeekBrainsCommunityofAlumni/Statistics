package test;


import com.gb.statistics.webservice.controller.SiteController;
import com.gb.statistics.webservice.entity.Site;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class SiteControllerTest {


    SiteController siteController = new SiteController();

    @Test
    void addSite() {

        Site addedSite = new Site(15, "testaddsite.ru", "testaddsite.ru/index");
        siteController.addSite(addedSite);
        Assert.assertEquals(siteController.mockSiteRepository.getById(15), addedSite);
    }

    @Test
    void getSite() {

        Site addedSite = new Site(20, "newsitename.ru", "newsitename.ru/index");

        siteController.addSite(addedSite);

        Assert.assertEquals(addedSite,  siteController.getSite(20));
    }

    @Test
    void getSiteReturnsNotNull() {
        Assert.assertNotNull(siteController.getSite(1));
    }

}