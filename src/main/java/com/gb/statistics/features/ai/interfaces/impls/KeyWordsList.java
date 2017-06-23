package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class KeyWordsList implements KeyWordsInterface {

    private String URL = "http://94.130.27.143:8080/api";

    private ObservableList<ModelListData> keyWordList;
    private RestTemplate template = new RestTemplate();
    private Person person;
    private HttpHeaders headers;

    public KeyWordsList() {
        Callback<ModelListData, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        keyWordList = FXCollections.observableArrayList(extractor);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
    }

    @Override
    public void refreshKeyWordList() {
        try {
            ResponseEntity<List<KeyWord>> rateResponse = template.exchange(URL + "/keyword/" + person.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<KeyWord>>() {
            });
            keyWordList.clear();
            keyWordList.setAll(rateResponse.getBody());
        } catch (HttpClientErrorException e) {
            keyWordList.clear();
        }
    }

    @Override
    public ObservableList<ModelListData> getKeyWordList() {
        return keyWordList;
    }

    @Override
    public boolean addKeyWord(KeyWord keyWord) {
        template.postForObject(URL + "/keyword/" + person.getId(), new HttpEntity<>(keyWord, headers), KeyWord.class);
        refreshKeyWordList();
        return false;
    }

    @Override
    public boolean updateKeyWord(KeyWord keyWord) {
        template.put(URL + "/keyword",  new HttpEntity<>(keyWord, headers), KeyWord.class);
        refreshKeyWordList();
        return false;
    }

    @Override
    public boolean deleteKeyWord(KeyWord keyWord) {
        template.delete(URL + "/keyword/" + keyWord.getId());
        refreshKeyWordList();
        return false;
    }

    @Override
    public void setPerson(Person person) {
        this.person = person;
    }
}
