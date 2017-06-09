package com.gb.statistics.webservice.entity;




public class Page extends AbstractModel {

    private String url;
    public Page(int id,String url){
        super(id);
        this.url = url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {

        return url;
    }
}
