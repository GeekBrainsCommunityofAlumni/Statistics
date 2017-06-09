package com.gb.statistics.webservice.entity;


public class Site {

    private int id;
    private String name;
    private String url;

    public Site(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
