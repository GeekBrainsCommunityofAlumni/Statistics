package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.model.Site;

public interface SiteRepositoryWrite extends CrudRepository {

    void addNewSite (Site site);

}
