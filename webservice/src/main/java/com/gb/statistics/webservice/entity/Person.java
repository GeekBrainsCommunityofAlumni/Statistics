package com.gb.statistics.webservice.entity;


public class Person extends AbstractModel {
    private String name;
    private int rank;

    public Person(int id, String name) {
        super(id);
        this.name = name;
    }
    public Person(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
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
        result = 31 * result + rank;
        return result;
    }
}
