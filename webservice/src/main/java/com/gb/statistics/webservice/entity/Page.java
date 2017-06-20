package com.gb.statistics.webservice.entity;


import java.util.Date;

public class Page {
    private Long id;
    private String url;
    private Long siteId;
//    private Date firstScan;
//    private Date lastScan;


    public Page(Long id, String url, Long siteId) {
        this.id = id;
        this.url = url;
        this.siteId = siteId;
    }
    public Page(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
}