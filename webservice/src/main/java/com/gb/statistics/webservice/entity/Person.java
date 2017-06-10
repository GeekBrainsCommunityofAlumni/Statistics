package com.gb.statistics.webservice.entity;


import java.util.List;

public class Person {
    private int id;
    private String name;
    private List<PersonPageRank> rank;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Person(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PersonPageRank> getRank() {
        return rank;
    }

    public void setRank(List<PersonPageRank> rank) {
        this.rank = rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (rank != person.rank) return false;
        return name.equals(person.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + rank.hashCode();
        return result;
    }
}
