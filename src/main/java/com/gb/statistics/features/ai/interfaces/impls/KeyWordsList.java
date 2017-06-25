package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.model.Person;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class KeyWordsList implements ListInterface {

    private ObservableList<ModelListData> keyWordList;
    private RestTemplate template = new RestTemplate();
    private Person person;
    private HttpHeaders headers;
    String URL;

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
        ResponseEntity<List<KeyWord>> rateResponse = template.exchange(URL + "/keyword/" + person.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<KeyWord>>() {
        });
        keyWordList.clear();
        keyWordList.setAll(rateResponse.getBody());
    }

    @Override
    public ObservableList<ModelListData> getList() {
        return keyWordList;
    }

    @Override
    public boolean add(ModelListData keyWord) {
        template.postForObject(URL + "/keyword/" + person.getId(), new HttpEntity<>(keyWord, headers), KeyWord.class);
        refreshList();
        return false;
    }

    @Override
    public boolean update(ModelListData keyWord) {
        template.put(URL + "/keyword",  new HttpEntity<>(keyWord, headers), KeyWord.class);
        refreshList();
        return false;
    }

    @Override
    public boolean delete(ModelListData keyWord) {
        template.delete(URL + "/keyword/" + keyWord.getId());
        refreshList();
        return false;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}