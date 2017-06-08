package com.gb.statistics.webservice.repository;


import com.gb.statistics.webservice.entity.AbstractModel;

import java.util.List;

public interface CrudRepository <T extends AbstractModel>{

    List<T> getAll();
    T getById (int id);
    void add (T model);
    void update (T model);
    void deleteById (int id);
}
