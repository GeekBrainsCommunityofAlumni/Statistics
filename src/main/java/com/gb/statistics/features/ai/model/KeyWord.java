package com.gb.statistics.features.ai.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class KeyWord {

    private int id;
    private StringProperty name;
    private int personId;

    public KeyWord() {
        this.name = new SimpleStringProperty("");
    }

    public KeyWord(int id, String name, int personId) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.personId = personId;
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

    public int getPersonId() {
        return personId;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
