package com.gb.statistics.features.ai.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class ModelListData {

    private int id;
    private StringProperty name;

    public ModelListData() {
        this.name = new SimpleStringProperty("");
    }

    public ModelListData(int id, String name) {
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
}
