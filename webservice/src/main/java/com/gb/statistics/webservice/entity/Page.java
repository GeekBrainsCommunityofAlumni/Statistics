package com.gb.statistics.webservice.entity;


import java.util.Date;

public class Page {
    private int id;
    private String url;
    private int siteId;
//    private Date firstScan;
//    private Date lastScan;


    public Page(int id, String url, int siteId) {
        this.id = id;
        this.url = url;
        this.siteId = siteId;
    }
    public Page(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }
}
