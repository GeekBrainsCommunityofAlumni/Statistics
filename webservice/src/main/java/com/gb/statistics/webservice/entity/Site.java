package com.gb.statistics.webservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="sites")
@NamedQuery(name="Site.findAll", query="SELECT s FROM Site s")
public class Site implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    private int id;

    @Column(nullable=false, length=256)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="site")
    private List<Page> pages;

    public Site() {
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

    public List<Page> getPages() {
        return this.pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    public Page addPage(Page page) {
        getPages().add(page);
        page.setSite(this);

        return page;
    }

    public Page removePage(Page page) {
        getPages().remove(page);
        page.setSite(null);

        return page;
    }

}