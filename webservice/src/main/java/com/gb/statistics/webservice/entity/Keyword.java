package com.gb.statistics.webservice.entity;


public class Keyword {
    private int id;
    private String person;
    private String name;

    public Keyword(String person, String name) {
        this.person = person;
        this.name = name;
    }
    public Keyword(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
