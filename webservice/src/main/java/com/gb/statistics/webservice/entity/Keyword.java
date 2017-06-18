package com.gb.statistics.webservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;
    private int personId;
    private String name;

    public Keyword(int personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public Keyword(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (!(o instanceof Keyword)) return false;

        Keyword keyword = (Keyword) o;

        if (getPersonId() != keyword.getPersonId()) return false;
        if (!getId().equals(keyword.getId())) return false;
        return getName().equals(keyword.getName());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getPersonId();
        result = 31 * result + getName().hashCode();
        return result;
    }
}