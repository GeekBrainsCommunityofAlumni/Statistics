package com.gb.statistics.webservice.entity;


public class Site {

    private int id;
    private String name;

    public Site(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Site(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Site site = (Site) o;

        if (id != site.id) return false;
        return name.equals(site.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        return result;
    }
}
