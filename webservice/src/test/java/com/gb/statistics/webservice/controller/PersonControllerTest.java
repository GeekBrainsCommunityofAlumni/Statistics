package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.MockPersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class PersonControllerTest {

    @Autowired
    PersonController personController;

    @Before
    public void setUp(){
        MockPersonRepository.personsList.add(new Person(1, "Путин"));
        MockPersonRepository.personsList.add(new Person(2, "Навальный"));
        MockPersonRepository.personsList.add(new Person(3, "Зюганов"));

    }

    @Test
    public void getPersonNotFoundTest() throws Exception {
        Assert.assertTrue(personController.getPerson(100500).getStatusCode().is4xxClientError());
    }



}