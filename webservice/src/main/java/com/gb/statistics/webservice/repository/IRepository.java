package com.gb.statistics.webservice.repository;




import java.util.List;


public interface IRepository<T> {

    List<T> getAll();

    T getById(int id);

    T add(T model);

    boolean delete(T model);

}
