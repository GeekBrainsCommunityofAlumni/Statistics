package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Keyword;
import com.gb.statistics.webservice.repository.MockKeywordRepository;
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


import java.util.*;

import static com.gb.statistics.webservice.controller.InitTestModel.*;


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

    @Test
    public void getByPersonTest() throws Exception {
        ResponseEntity responseEntity = keywordController.getByPerson(EXISTING_ID);
        List<Keyword> resultList = (List<Keyword>) responseEntity.getBody();
        List<Keyword> listInMock = new LinkedList<Keyword>();
        for (Keyword k : MockKeywordRepository.keywordList) {
            if (k.getPersonId() == EXISTING_ID) {
                listInMock.add(k);
            }
        }
        Assert.assertTrue(resultList.containsAll(listInMock));
    }

    @Test
    public void testAddNullKeywordOrPerson() throws Exception {
        Assert.assertTrue(keywordController.add(new Keyword(0, NOT_EXISTING_KEYWORD))
                .getStatusCode().is4xxClientError());
        Assert.assertTrue(keywordController.add(new Keyword(EXISTING_ID, null))
                .getStatusCode().is4xxClientError());
    }

    @Test
    public void addKeywordIsExistTest() throws Exception {
        Assert.assertTrue(keywordController.add(new Keyword(EXISTING_ID, EXISTING_KEYWORD))
                .getStatusCode().is4xxClientError());
    }

    @Test
    public void addKeywordReturnOk() throws Exception {
        Assert.assertTrue(keywordController.add(new Keyword(EXISTING_ID, NOT_EXISTING_KEYWORD))
                .getStatusCode().is2xxSuccessful());
    }

    @Ignore // MockKeywordRepository isExist() strange
    @Test
    public void updateKeywordReturnOk() throws Exception {
        Assert.assertTrue(keywordController.update(new Keyword(EXISTING_ID, UPDATED_KEYWORD))
                .getStatusCode().is2xxSuccessful());
    }



}