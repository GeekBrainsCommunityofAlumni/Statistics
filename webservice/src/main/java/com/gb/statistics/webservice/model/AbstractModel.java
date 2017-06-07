package com.gb.statistics.webservice.model;


public abstract class AbstractModel {

    public int id;

    public AbstractModel(){
    }

    public AbstractModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
