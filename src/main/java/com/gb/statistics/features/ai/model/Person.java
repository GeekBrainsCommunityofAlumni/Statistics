package com.gb.statistics.features.ai.model;

public class Person {

    private int id;
    private KeyWordsList keyWordsList;

    public Person(int id, KeyWordsList keyWordsList) {
        this.id = id;
        this.keyWordsList = keyWordsList;
    }

    public int getId() {
        return id;
    }

    public KeyWordsList getKeyWordsList() {
        return keyWordsList;
    }
}
