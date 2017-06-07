package com.gb.statistics.webservice.entity;


public class Person {
    private int id;
    private String name;
    private int rank;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person(){}

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
