package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Keyword;
import com.gb.statistics.webservice.repository.MockKeywordRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import java.util.*;

import static com.gb.statistics.webservice.controller.InitTestModel.init;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class KeywordControllerTest {

    @Autowired
    private KeywordController keywordController;

    @Before
    public void startUp() {
        init();
    }

    @Test
    public void getAllKeywordsTest() throws Exception {
        ResponseEntity responseEntity = keywordController.getAll();
        List<Keyword> resultList = (List<Keyword>) responseEntity.getBody();
        Assert.assertTrue(resultList.containsAll(MockKeywordRepository.keywordList));
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
    


}