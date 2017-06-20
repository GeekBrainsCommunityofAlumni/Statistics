package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Person;
import com.gb.statistics.webservice.entity.Personpagerank;
import com.gb.statistics.webservice.entity.PersonpagerankPK;
import com.gb.statistics.webservice.entity.Site;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StatRepository extends Repository<Personpagerank, PersonpagerankPK> {
    List<Personpagerank> findByPageSite(Site site);
    List<Personpagerank> findByPageSiteAndPerson(Site site, Person person);

    @Query("select p from Personpagerank p " +
            "where p.page.site = :s and " +
            "p.person = :pers and " +
            "date(p.page.founddatetime) = date(:date)")
    List<Personpagerank> findByPageSiteAndPersonAndPageFounddatetime(@Param("s") Site s,
                                                                     @Param("pers") Person p,
                                                                     @Param("date") Date d);
    List<Personpagerank> findByPageSiteAndPersonAndPageFounddatetimeBetween(Site s, Person p, Date d, Date d2);
}
