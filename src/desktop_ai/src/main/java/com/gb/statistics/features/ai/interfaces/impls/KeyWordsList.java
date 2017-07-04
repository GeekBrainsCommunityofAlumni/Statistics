package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.controllers.KeyWordsListController;
import com.gb.statistics.features.ai.controllers.ListController;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.interfaces.ListInterface;
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

public class KeyWordsList implements ListInterface {

    private ObservableList<ModelListData> keyWordList;
    private RestTemplate template = new RestTemplate();
    private ListController controller;
    private Person person;
    private HttpHeaders headers;
    private String URL;

    public KeyWordsList(String url) {
        Callback<ModelListData, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        this.URL = url;
        keyWordList = FXCollections.observableArrayList(extractor);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
    }

    @Override
    public void refreshList() {
        ResponseEntity<List<KeyWord>> rateResponse;
        try {
            rateResponse = template.exchange(URL + "/keyword/" + person.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<KeyWord>>() {
            });
        } catch (HttpClientErrorException e) {
            rateResponse = null;
            if (!e.getMessage().equals("404 null")) controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            rateResponse = null;
            controller.getConnectionController().disconnect();
        }
        keyWordList.clear();
        if (rateResponse != null) keyWordList.setAll(rateResponse.getBody());
        refreshFilter();
    }

    @Override
    public ObservableList<ModelListData> getList() {
        return keyWordList;
    }

    @Override
    public boolean add(ModelListData keyWord) {
        try {
            template.postForObject(URL + "/keyword/" + person.getId(), new HttpEntity<>(keyWord, headers), KeyWord.class);
            controller.setErrorMessage("");
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
            ((KeyWordsListController)controller).refreshPersonList();
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return false;
    }

    @Override
    public boolean update(ModelListData keyWord) {
        try {
            template.put(URL + "/keyword",  new HttpEntity<>(keyWord, headers), KeyWord.class);
            controller.setErrorMessage("");
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
            ((KeyWordsListController)controller).refreshPersonList();
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return false;
    }

    @Override
    public boolean delete(ModelListData keyWord) {
        try {
            template.delete(URL + "/keyword/" + keyWord.getId());
            controller.setErrorMessage("");
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
            ((KeyWordsListController)controller).refreshPersonList();
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return false;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setController(ListController controller) {
        this.controller = controller;
    }

    private void refreshFilter() {
        String text = controller.getSearchText();
        controller.setSearchText("");
        controller.setSearchText(text);
    }
}