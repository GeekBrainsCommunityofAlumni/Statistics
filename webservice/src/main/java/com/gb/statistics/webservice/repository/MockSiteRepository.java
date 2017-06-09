package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Site;

import java.util.*;

public class MockSiteRepository implements SiteRepository {

    private Map<Integer, Site> siteTableMock = Collections.EMPTY_MAP;

    public MockSiteRepository() {
        siteTableMock = new HashMap<Integer, Site>();
        siteTableMock.put(1, new Site(1, "lenta.ru", "www.lenta.ru"));
        siteTableMock.put(2, new Site(2, "ria.ru", "www.ria.ru/index"));

    }

    @Override
    public List<Site> getAll() {
        return (List) siteTableMock.values();

    }

    @Override
    public Site getById(int id) {
        return siteTableMock.get(id);
    }


    @Override
    public Site add(Site site) {
        siteTableMock.put(site.getId(), site);
        return site;
    }

    @Override
    public boolean delete(Site site) {

        siteTableMock.remove(site);
        return true;
    }

    @Override
    public String getUrl(int id) {
        Site site = siteTableMock.get(id);
        return site.getUrl();
    }
}
