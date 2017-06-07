package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Site;

import java.util.List;

public interface SiteRepository {



    Site getSite(int id);
    List<Site> getAllSites();

}
