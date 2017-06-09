package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Site;

import java.util.List;

public interface SiteRepository {

    Site get(int id);
    List<Site> getAll();
    Site add(Site site);
    Site update(Site site);
    boolean delete(Site site);
}
