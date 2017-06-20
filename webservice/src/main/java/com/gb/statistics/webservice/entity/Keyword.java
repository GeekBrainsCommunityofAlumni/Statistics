package com.gb.statistics.webservice.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="keywords")
@NamedQuery(name="Keyword.findAll", query="SELECT k FROM Keyword k")
public class Keyword implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int id;

    @Column(nullable=false, length=2048)
    private String name;

    @ManyToOne
    @JoinColumn(name="PersonID", nullable=false)
    private Person person;

    public Keyword() {
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

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}