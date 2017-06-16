package com.gb.statistics.webservice.controller;

import com.gb.statistics.webservice.AppConfig;
import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.repository.MockPersonRepository;
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

import static com.gb.statistics.webservice.controller.InitTestModel.NOT_EXISTING_TEST_ID;



@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class})
public class PersonControllerTest {



    @Autowired
    private PersonController personController;

    @Before
    public void setUp(){
        InitTestModel.init();
    }

    @Test
    public void getPersonNotFoundTest() throws Exception {
        Assert.assertTrue(personController.getPerson(NOT_EXISTING_TEST_ID).getStatusCode().is4xxClientError());
    }

    @Test
    public void getPersonEqualsMock() throws Exception {
        Person person = MockPersonRepository.personsList.get(0);
        Assert.assertEquals(person, personController.getPerson(person.getId()).getBody());
    }

    @Test
    public void getAllPersonsTest() throws Exception {
        ResponseEntity<List<Person>> responseEntity = personController.getAllPersons();
        List<Person> resultList = responseEntity.getBody();
        Assert.assertTrue(MockPersonRepository.personsList.containsAll(resultList));
    }

    @Test
    public void addPersonExisting() throws Exception {
        Person existingPerson = new Person(1, "Путин");
        ResponseEntity responseEntity = personController.addPerson(existingPerson);
        Assert.assertTrue(responseEntity.getStatusCode().is4xxClientError());
    }

    @Test
    public void addPersonResponseIsOk() throws Exception {
        Person personToAdd = new Person(NOT_EXISTING_TEST_ID, "Медведев");
        ResponseEntity responseEntity = personController.addPerson(personToAdd);
        Assert.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void addPersonContainsMock() throws Exception {
        Person personToAdd = new Person(NOT_EXISTING_TEST_ID, "Медведев");
        personController.addPerson(personToAdd);
        Assert.assertTrue(MockPersonRepository.personsList.contains(personToAdd));
    }

    @Test
    public void updatePerson() throws Exception {

    }
}