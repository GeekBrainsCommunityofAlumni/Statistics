package com.gb.statistics.webservice.Entitys;


public class Person {
    private int id;
    private String name;
    private int rank;

    public Person(int id, String name, int rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
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
