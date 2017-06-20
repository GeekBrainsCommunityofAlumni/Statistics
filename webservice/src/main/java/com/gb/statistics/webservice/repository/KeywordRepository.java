package com.gb.statistics.webservice.repository;

import com.gb.statistics.webservice.entity.Keyword;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KeywordRepository extends CrudRepository<Keyword, Integer> {
    List<Keyword> findByPersonId(Integer id);
}
