package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Site;

import java.util.LinkedList;
import java.util.List;

public class MockSiteRepository implements SiteRepository {


    @Override
    public List<Site> getAll() {
        List<Site> site = new LinkedList<Site>();
        site.add(new Site(1, "lenta.ru"));
        site.add(new Site(2, "ria.ru"));
        return site;
    }

    @Override
    public Site getById(int id) {
        return new Site(id, "Test.ru");
    }

    @Override
    public Site add(Site site) {
        if(site.getName().equals("mk.ru")) return new Site(3, "mk.ru");
        return null;
    }

    @Override
    public boolean delete(Site site) {
        return site.getId()<100;
    }
}
