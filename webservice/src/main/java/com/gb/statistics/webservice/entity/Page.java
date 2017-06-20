package com.gb.statistics.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="pages")
@NamedQuery(name="Page.findAll", query="SELECT p FROM Page p")
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(nullable=false)
    private Date founddatetime;

    @Temporal(TemporalType.DATE)
    private Date lastscandate;

    @Column(nullable=false, length=2048)
    private String url;

    @ManyToOne
    @JoinColumn(name="siteid", nullable=false)
    private Site site;

    @JsonIgnore
    @OneToMany(mappedBy="page")
    private List<Personpagerank> personpageranks;

    public Page() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFounddatetime() {
        return this.founddatetime;
    }

    public void setFounddatetime(Date founddatetime) {
        this.founddatetime = founddatetime;
    }

    public Date getLastscandate() {
        return this.lastscandate;
    }

    public void setLastscandate(Date lastscandate) {
        this.lastscandate = lastscandate;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Site getSite() {
        return this.site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public List<Personpagerank> getPersonpageranks() {
        return this.personpageranks;
    }

    public void setPersonpageranks(List<Personpagerank> personpageranks) {
        this.personpageranks = personpageranks;
    }

    public Personpagerank addPersonpagerank(Personpagerank personpagerank) {
        getPersonpageranks().add(personpagerank);
        personpagerank.setPage(this);

        return personpagerank;
    }

    public Personpagerank removePersonpagerank(Personpagerank personpagerank) {
        getPersonpageranks().remove(personpagerank);
        personpagerank.setPage(null);

        return personpagerank;
    }

}