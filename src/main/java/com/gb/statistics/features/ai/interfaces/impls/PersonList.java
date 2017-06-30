package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.controllers.ConnectionController;
import com.gb.statistics.features.ai.controllers.ListController;
import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class PersonList implements ListInterface {

    private ObservableList<ModelListData> personList;
    private RestTemplate template = new RestTemplate();
    private ResponseEntity<List<Person>> rateResponse;
    private ListController controller;
    private HttpHeaders headers;
    private String URL;

    public PersonList(String url) {
        this.URL = url;
        Callback<ModelListData, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        personList = FXCollections.observableArrayList(extractor);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
    }

    public void refreshList() {
        try {
            rateResponse = template.exchange(URL + "/person", HttpMethod.GET, null, new ParameterizedTypeReference<List<Person>>() {
            });
        } catch (HttpClientErrorException e) {
            rateResponse = null;
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            rateResponse = null;
            controller.getConnectionController().disconnect();
        }
        personList.clear();
        if (rateResponse != null) personList.setAll(rateResponse.getBody());
    }

    public boolean add(ModelListData person) {
        try {
            template.postForObject(URL + "/person", new HttpEntity<>(person, headers), Person.class);
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return true;
    }

    public boolean update(ModelListData person) {
        try {
            template.put(URL + "/person", new HttpEntity<>(person, headers), Person.class);
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return false;
    }

    public boolean delete(ModelListData person) {
        try {
            template.delete(URL + "/person/" + person.getId());
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return true;
    }

    public ObservableList<ModelListData> getList() {
        return personList;
    }

    @Override
    public void setController(ListController controller) {
        this.controller = controller;
    }
}