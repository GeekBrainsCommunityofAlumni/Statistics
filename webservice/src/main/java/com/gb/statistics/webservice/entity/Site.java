package com.gb.statistics.webservice.entity;


public class Site extends AbstractModel {

    private String name;
    private String url;

    public Site(int id, String name, String url) {
        super(id);
        this.name = name;
        this.url = url;
    }

//    public Site(int id, String name) {
//        super(id);
//        this.name = name;
//
//    }

    public Site(){};


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
