package com.gb.statistics.webservice.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Keyword {
    @Id
    @GeneratedValue
    private Long id;
    private Long personId;
    private String name;

    public Keyword(Long personId, String name) {
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

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
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

        if (!getId().equals(keyword.getId())) return false;
        if (!getPersonId().equals(keyword.getPersonId())) return false;
        return getName().equals(keyword.getName());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getPersonId().hashCode();
        result = 31 * result + getName().hashCode();
        return result;
    }
}
