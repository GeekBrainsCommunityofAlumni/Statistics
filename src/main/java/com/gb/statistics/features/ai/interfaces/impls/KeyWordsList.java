package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.KeyWordsInterface;
import com.gb.statistics.features.ai.model.KeyWord;
import com.gb.statistics.features.ai.model.Person;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class KeyWordsList implements KeyWordsInterface {

    private String URL = "http://127.0.0.1:8080/";

    private ObservableList<KeyWord> keyWordList;
    private RestTemplate template = new RestTemplate();
    private Person person;

    public KeyWordsList() {
        Callback<KeyWord, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        keyWordList = FXCollections.observableArrayList(extractor);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    public void refreshKeyWordList() {
        ResponseEntity<List<KeyWord>> rateResponse = template.exchange(URL + "/keyword/" + person.getId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<KeyWord>>() {
        });
        keyWordList.clear();
        keyWordList.setAll(rateResponse.getBody());
    }

    @Override
    public ObservableList<KeyWord> getKeyWordList() {
        return keyWordList;
    }

    @Override
    public boolean addKeyWord(KeyWord keyWord) {
        template.postForObject(URL + "/keyword", keyWord, KeyWord.class);
        refreshKeyWordList();
        return false;
    }

    @Override
    public boolean updateKeyWord(KeyWord keyWord) {
        template.put(URL + "/keyword", keyWord, KeyWord.class);
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
