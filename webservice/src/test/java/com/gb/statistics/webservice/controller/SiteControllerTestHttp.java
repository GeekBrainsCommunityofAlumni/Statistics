package com.gb.statistics.webservice.controller;


import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.repository.MockSiteRepository;
import com.gb.statistics.webservice.repository.SiteRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;


import static org.mockito.Mockito.mock;

//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = {AppConfig.class})

public class SiteControllerTestHttp {

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;

    private HandlerAdapter handlerAdapter;

    private SiteController siteController;

    private SiteRepository siteRepository;

    private MockSiteRepository mockSiteRepository;


    @Before
    public void setUp() throws NoSuchFieldException {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        handlerAdapter = new AnnotationMethodHandlerAdapter();
        siteRepository = mock(SiteRepository.class);
        mockSiteRepository = mock(MockSiteRepository.class);
        siteController = new SiteController();
        siteController = mock(SiteController.class);
    }

    @Test
    public void testHttpGetAllSite() throws Exception {
        request.setRequestURI("/site");
        request.setMethod("GET");

        final ModelAndView modelAndView= handlerAdapter.handle(request,response,siteController);
        Assert.assertNotNull(modelAndView.getModel().size());
    }


}
