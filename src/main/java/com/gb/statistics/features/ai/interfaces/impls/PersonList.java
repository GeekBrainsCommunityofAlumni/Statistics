package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.PersonListInterface;
import com.gb.statistics.features.ai.model.Person;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class PersonList implements PersonListInterface {

    private String URL = "http://94.130.27.143:8080/api";

    private ObservableList<Person> personList;
    private RestTemplate template = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    public PersonList() {
        Callback<Person, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        personList = FXCollections.observableArrayList(extractor);
        template.getInterceptors().add(new AuthHeaderInterceptor());
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public void refreshPersonList() {
        ResponseEntity<List<Person>> rateResponse = template.exchange(URL + "/person", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
        });
        personList.clear();
        personList.setAll(rateResponse.getBody());
    }

    public boolean addPerson(Person person) {
        template.postForObject(URL + "/person", person, Person.class);
        refreshPersonList();
        return true;
    }

    public boolean updatePerson(Person person) {
        template.put(URL + "/person", person, Person.class);
        refreshPersonList();
        return false;
    }

    public boolean deletePerson(Person person) {
        template.delete(URL + "/person/" + person.getId());
        refreshPersonList();
        return true;
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }
}