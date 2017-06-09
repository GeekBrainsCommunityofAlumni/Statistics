package com.gb.statistics.webservice.entity;


public class Page {

    private int id;
    private String url;

    public Page(int id, String url) {
        this.id = id;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }
}
