package com.gb.statistics.features.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {

    private int id;
    private StringProperty name = new SimpleStringProperty("");

    public Person() {
    }

    public Person(int id, String name) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
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

    @Override
    public String toString() {
        return name.get();
    }
}
