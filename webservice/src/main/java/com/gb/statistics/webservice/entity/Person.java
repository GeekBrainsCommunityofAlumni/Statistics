package com.gb.statistics.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="persons")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int id;

    @Column(nullable=false, length=2048)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="person")
    private List<Keyword> keywords;

    @JsonIgnore
    @OneToMany(mappedBy="person")
    private List<Personpagerank> personpageranks;

    public Person() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Keyword> getKeywords() {
        return this.keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Keyword addKeyword(Keyword keyword) {
        getKeywords().add(keyword);
        keyword.setPerson(this);

        return keyword;
    }

    public Keyword removeKeyword(Keyword keyword) {
        getKeywords().remove(keyword);
        keyword.setPerson(null);

        return keyword;
    }

    public List<Personpagerank> getPersonpageranks() {
        return this.personpageranks;
    }

    public void setPersonpageranks(List<Personpagerank> personpageranks) {
        this.personpageranks = personpageranks;
    }

    public Personpagerank addPersonpagerank(Personpagerank personpagerank) {
        getPersonpageranks().add(personpagerank);
        personpagerank.setPerson(this);

        return personpagerank;
    }

    public Personpagerank removePersonpagerank(Personpagerank personpagerank) {
        getPersonpageranks().remove(personpagerank);
        personpagerank.setPerson(null);

        return personpagerank;
    }

}