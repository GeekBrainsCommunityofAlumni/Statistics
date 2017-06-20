package com.gb.statistics.webservice.entity;


public class Site {

    private Long id;
    private String name;
    private String url;

    public Site(Long id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }
    public Site(){};

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (id != site.id) return false;
        if (!name.equals(site.name)) return false;
        return url.equals(site.url);
    }

    @Override
    public int hashCode() {
        int result = id.intValue();
        result = 31 * result + name.hashCode();
        result = 31 * result + url.hashCode();
        return result;
    }
}
