package com.gb.statistics.features.ai.interfaces.impls;

import com.gb.statistics.features.ai.interfaces.SiteListInterface;
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
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SiteList implements SiteListInterface {

    private String URL = "http://94.130.27.143:8080/api";

    private ObservableList<Site> siteList;
    private RestTemplate template = new RestTemplate();
    private HttpHeaders headers;

    public SiteList() {
        Callback<Site, Observable[]> extractor = s -> new Observable[] {s.getNameProperty()};
        siteList = FXCollections.observableArrayList(extractor);
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        headers = new HttpHeaders();
        headers.set("Content-Type", "application/json;charset=UTF-8");
    }

    public void refreshSiteList() {
        ResponseEntity<List<Site>> rateResponse = template.exchange(URL + "/site", HttpMethod.GET, null, new ParameterizedTypeReference<List<Site>>() {
        });
        siteList.clear();
        siteList.setAll(rateResponse.getBody());
    }

    public boolean addSite(Site site) {
        template.postForObject(URL + "/site", new HttpEntity<>(site, headers), Person.class);
        refreshSiteList();
        return true;
    }

    public boolean updateSite(Site site) {
        template.put(URL + "/site", new HttpEntity<>(site, headers), Person.class);
        refreshSiteList();
        return false;
    }

    public boolean deleteSite(Site site) {
        template.delete(URL + "/site/" + site.getId());
        refreshSiteList();
        return true;
    }

    public ObservableList<Site> getSiteList() {
        return siteList;
    }
}