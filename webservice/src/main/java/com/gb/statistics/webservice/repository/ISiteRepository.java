package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entitys.Site;

import java.util.List;

public interface ISiteRepository {



    Site getSite(int id);
    List<Site> getAllSites();

}
