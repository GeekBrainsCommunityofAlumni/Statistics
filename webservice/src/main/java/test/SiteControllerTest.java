package test;


import com.gb.statistics.webservice.controller.SiteController;
import com.gb.statistics.webservice.entity.Site;
import com.gb.statistics.webservice.repository.MockSiteRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;



class SiteControllerTest {


    SiteController siteController = new SiteController();
//    MockSiteRepository mockSiteRepository = new MockSiteRepository();

    @Test
    void addSite() {

        Site addedSite = new Site(15, "testaddsite.ru");
        siteController.addSite(addedSite);
        Assert.assertEquals(siteController.mockSiteRepository.getById(15), addedSite);
    }

    @Test
    void getSite() {

        Site addedSite = new Site(20, "newsitename.ru");

        siteController.addSite(addedSite);

        Assert.assertEquals(addedSite,  siteController.getSite(20));
    }

    @Test
    void getSiteReturnsNotNull() {
        Assert.assertNotNull(siteController.getSite(1));
    }


}