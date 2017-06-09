package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.Keyword;

import java.util.List;

public interface KeywordRepositiory {

    List<Keyword> getAll();
    List<Keyword> get(int personId);
    Keyword add(Keyword keyword);
    Keyword update(Keyword keyword);
    boolean delete(Keyword keyword);
    boolean isExists(Keyword keyword);
}
