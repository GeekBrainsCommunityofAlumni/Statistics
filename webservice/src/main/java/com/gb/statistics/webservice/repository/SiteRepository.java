package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Site;

import java.util.List;

public interface SiteRepository {

    Site get(int id);
    List<Site> getAll();
    Site add(String name, String url);
    Site update(Site site);
    boolean delete(int id);
}
