package com.gb.statistics.features.ai.model;

public class Person {

    private int id;
    private String name;
    //private KeyWordsList keyWordsList;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        //this.keyWordsList = keyWordsList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
