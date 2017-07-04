package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.model.ModelListData;
import com.gb.statistics.features.ai.controllers.ListController;
import com.gb.statistics.features.ai.interfaces.ListInterface;
import com.gb.statistics.features.ai.model.Person;
import com.gb.statistics.features.ai.model.Site;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

public class SiteList implements ListInterface {

    private ObservableList<ModelListData> siteList;
    private RestTemplate template = new RestTemplate();
    private ListController controller;
    private HttpHeaders headers;
    private String URL;

    public SiteList(String url) {
        this.URL = url;
        Callback<ModelListData, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        siteList = FXCollections.observableArrayList(extractor);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
    }

    public void refreshList() {
        ResponseEntity<List<Site>> rateResponse;
        try {
            rateResponse = template.exchange(URL + "/site", HttpMethod.GET, null, new ParameterizedTypeReference<List<Site>>() {
            });
        } catch (HttpClientErrorException e) {
            rateResponse = null;
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            rateResponse = null;
            controller.getConnectionController().disconnect();
        }
        siteList.clear();
        if (rateResponse != null) siteList.setAll(rateResponse.getBody());
        refreshFilter();
    }

    public boolean add(ModelListData site) {
        try {
            template.postForObject(URL + "/site", new HttpEntity<>(site, headers), Person.class);
            controller.setErrorMessage("");
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return true;
    }

    public boolean update(ModelListData site) {
        try {
            template.put(URL + "/site", new HttpEntity<>(site, headers), Person.class);
            controller.setErrorMessage("");
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return false;
    }

    public boolean delete(ModelListData site) {
        try {
            template.delete(URL + "/site/" + site.getId());
            controller.setErrorMessage("");
        } catch (HttpClientErrorException e) {
            controller.setErrorMessage(e.getResponseBodyAsString());
        } catch (ResourceAccessException e) {
            controller.getConnectionController().disconnect();
        }
        refreshList();
        return true;
    }

    public ObservableList<ModelListData> getList() {
        return siteList;
    }

    @Override
    public void setController(ListController controller) {
        this.controller = controller;
    }

    private void refreshFilter() {
        String text = controller.getSearchText();
        controller.setSearchText("");
        controller.setSearchText(text);
    }
}