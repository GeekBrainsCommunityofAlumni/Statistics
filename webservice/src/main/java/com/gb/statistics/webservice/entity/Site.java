package com.gb.statistics.webservice.entity;


public class Site extends AbstractModel {


    private String name;

    public Site(int id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
