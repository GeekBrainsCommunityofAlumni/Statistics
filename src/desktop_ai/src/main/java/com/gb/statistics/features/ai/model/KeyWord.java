package com.gb.statistics.features.ai.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyWord extends ModelListData {

    private int personId;

    public KeyWord() {
        super();
    }

    public KeyWord(int id, int personId, String name) {
        super(id, name);
        this.personId = personId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}