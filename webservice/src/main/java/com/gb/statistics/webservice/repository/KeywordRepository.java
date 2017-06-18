package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Keyword;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface KeywordRepository extends CrudRepository<Keyword,Long>{

    List<Keyword> getByPersonId(int personId);
    @Modifying
    Keyword update(Keyword keyword);                //вот может быть это даже будет работать

}
