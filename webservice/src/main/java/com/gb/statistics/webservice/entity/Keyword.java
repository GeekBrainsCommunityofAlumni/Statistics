package com.gb.statistics.webservice.entity;


public class Keyword {

    private int id;
    private int personId;
    private String name;

    public Keyword(int personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public Keyword(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Keyword keyword = (Keyword) o;

        if (id != keyword.id) return false;
        if (personId != keyword.personId) return false;
        return name.equals(keyword.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + personId;
        result = 31 * result + name.hashCode();
        return result;
    }
}
