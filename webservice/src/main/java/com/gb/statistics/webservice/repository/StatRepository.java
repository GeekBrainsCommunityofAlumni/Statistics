package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.entity.Personpagerank;
import com.gb.statistics.webservice.entity.PersonpagerankPK;
import com.gb.statistics.webservice.entity.Site;
import org.springframework.data.repository.Repository;

import java.util.Date;
import java.util.List;

public interface StatRepository extends Repository<Personpagerank, PersonpagerankPK> {
    List<Personpagerank> findByPageSite(Site site);
    List<Personpagerank> findByPageSiteAndPerson(Site site, Person person);
    List<Personpagerank> findByPageSiteAndPersonAndPageFounddatetime(Site s, Person p, Date d); //AndRankNot Integer 0
    List<Personpagerank> findByPageSiteAndPersonAndPageFounddatetimeBetween(Site s, Person p, Date d, Date d2);
}
