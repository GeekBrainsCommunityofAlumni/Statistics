package com.gb.statistics.webservice.entity;


public class Person extends AbstractModel {
    private String name;
    private int rank;

    public Person(int id, String name) {
        super(id);
        this.name = name;
    }
    public Person(){}


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
