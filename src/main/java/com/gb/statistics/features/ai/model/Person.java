package com.gb.statistics.features.ai.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

    private int id;
    private StringProperty name = new SimpleStringProperty("");
    //private KeyWordsList keyWordsList;


    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        //this.keyWordsList = keyWordsList;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
