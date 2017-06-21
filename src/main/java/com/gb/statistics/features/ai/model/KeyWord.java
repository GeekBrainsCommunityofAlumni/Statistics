package com.gb.statistics.features.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyWord {

    private int id;
    private int personId;
    private StringProperty name;

    public KeyWord() {
        this.name = new SimpleStringProperty("");
    }

    public KeyWord(int id, int personId, String name) {
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

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
